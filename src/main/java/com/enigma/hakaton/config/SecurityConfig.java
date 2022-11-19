package com.enigma.hakaton.config;

import com.enigma.hakaton.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.Http403ForbiddenEntryPoint;
import org.springframework.security.web.authentication.session.ConcurrentSessionControlAuthenticationStrategy;
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.session.RequestedUrlRedirectInvalidSessionStrategy;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(
        prePostEnabled = true,
        securedEnabled = true,
        jsr250Enabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserService userService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public SecurityConfig(UserService userService, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userService = userService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    @Bean(name = BeanIds.AUTHENTICATION_MANAGER)
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(userService);
        daoAuthenticationProvider.setPasswordEncoder(bCryptPasswordEncoder);
        daoAuthenticationProvider.setHideUserNotFoundExceptions(false);
        auth
                .authenticationProvider(daoAuthenticationProvider);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/", "/login", "/logout", "/js/**", "/error**").permitAll()
                .anyRequest().authenticated()
                .and().formLogin().loginProcessingUrl("/login")
                .and().logout().logoutSuccessUrl("/").permitAll()
                .and()
                .csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse());
        http.sessionManagement()
                .sessionFixation().newSession()
                .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                .sessionAuthenticationStrategy(sessionAuthenticationStrategy())
                .invalidSessionStrategy(new RequestedUrlRedirectInvalidSessionStrategy())
                .enableSessionUrlRewriting(true)
                .maximumSessions(1)
                .expiredSessionStrategy(event -> {
                    event.getRequest().logout();
                    String destinationUrl = ServletUriComponentsBuilder
                            .fromRequest(event.getRequest())
                            .host(null)
                            .scheme(null)
                            .port(null)
                            .toUriString();
                    event.getResponse().sendRedirect(destinationUrl);
                })
                .maxSessionsPreventsLogin(false)
                .sessionRegistry(sessionRegistry());
    }

    @Bean
    Http403ForbiddenEntryPoint http403ForbiddenEntryPoint() {
        return new Http403ForbiddenEntryPoint();
    }

    @Bean
    public SessionRegistry sessionRegistry() {
        return new SessionRegistryImpl();
    }

    @Bean
    public SessionAuthenticationStrategy sessionAuthenticationStrategy() {
        return new ConcurrentSessionControlAuthenticationStrategy(sessionRegistry());
    }
}
