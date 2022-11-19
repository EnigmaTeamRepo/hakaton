package com.enigma.hakaton.service;

import com.enigma.hakaton.exception.LoginAlreadyExistException;
import com.enigma.hakaton.exception.PasswordNonMatchException;
import com.enigma.hakaton.helper.ApplicationHelper;
import com.enigma.hakaton.model.User;
import com.enigma.hakaton.model.enums.Role;
import com.enigma.hakaton.model.enums.UserStatus;
import com.enigma.hakaton.model.request.RegisterRequest;
import com.enigma.hakaton.model.request.UserIdJsonRequest;
import com.enigma.hakaton.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Objects;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Value("${spring.admin.login:admin}")
    private String adminLogin;
    @Value("${spring.admin.pass:admin}")
    private String adminPass;


    @Autowired
    public UserService(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByLogin(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not exist");
        }
        user.getAuthorities().add(new SimpleGrantedAuthority("ROLE_" + user.getRole().name()));
        return user;
    }

    public List<User> getAllUsers() {
        return userRepository.findAllByRole(Role.USER);
    }

    public Boolean registerNew(RegisterRequest registerRequest) {
        if (userRepository.findByLogin(registerRequest.getLogin()) != null) {
            throw new LoginAlreadyExistException();
        }
        if (!Objects.equals(registerRequest.getPassword(), registerRequest.getPassword2())) {
            throw new PasswordNonMatchException();
        }
        User user = new User();
        user.setLogin(registerRequest.getLogin());
        user.setPassword(bCryptPasswordEncoder.encode(registerRequest.getPassword()));
        user.setRole(Role.USER);
        ApplicationHelper.addUserInformation(user);
        userRepository.save(user);
        return user.getId() != null;
    }

    private void registerDefaultAdmin() {
        User defaultAdmin = new User();
        defaultAdmin.setLogin(adminLogin);
        defaultAdmin.setPassword(bCryptPasswordEncoder.encode(adminPass));
        defaultAdmin.setRole(Role.ADMIN);
        defaultAdmin.setUserStatus(UserStatus.ACTIVE);
        defaultAdmin.setRegistrationDate(new Date());
        userRepository.save(defaultAdmin);
    }

    public Boolean approveUserRegistration(UserIdJsonRequest request) {
        User user = userRepository.findById(request.getUserId()).orElseThrow();
        user.setUserStatus(UserStatus.ACTIVE);
        return userRepository.save(user).getUserStatus().equals(UserStatus.ACTIVE);
    }

    public Boolean blockUser(UserIdJsonRequest request) {
        User user = userRepository.findById(request.getUserId()).orElseThrow();
        if (UserStatus.BLOCKED.equals(user.getUserStatus())) {
            return false;
        }
        user.setUserStatus(UserStatus.BLOCKED);
        return userRepository.save(user).getUserStatus().equals(UserStatus.BLOCKED);
    }

    public Boolean unblockUser(UserIdJsonRequest request) {
        User user = userRepository.findById(request.getUserId()).orElseThrow();
        if (UserStatus.ACTIVE.equals(user.getUserStatus()) || UserStatus.NEW.equals(user.getUserStatus())) {
            return false;
        }
        user.setUserStatus(UserStatus.ACTIVE);
        return userRepository.save(user).getUserStatus().equals(UserStatus.ACTIVE);
    }
}
