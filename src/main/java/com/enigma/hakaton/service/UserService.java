package com.enigma.hakaton.service;

import com.enigma.hakaton.exception.LoginAlreadyExistException;
import com.enigma.hakaton.model.User;
import com.enigma.hakaton.model.request.RegisterRequest;
import com.enigma.hakaton.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;


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

    public Boolean registerNew(RegisterRequest registerRequest) {
        if (userRepository.findByLogin(registerRequest.getLogin()) != null) {
            throw new LoginAlreadyExistException();
        }
        User user = new User();
        user.setLogin(registerRequest.getLogin());
        user.setPassword(bCryptPasswordEncoder.encode(registerRequest.getPassword()));
        userRepository.save(user);
        return user.getId() != null;
    }
}
