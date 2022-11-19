package com.enigma.hakaton.repository;

import com.enigma.hakaton.model.User;
import com.enigma.hakaton.model.enums.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByLogin(String login);

    List<User> findAllByRole(Role role);
}
