package com.enigma.hakaton.controller.rest;

import com.enigma.hakaton.model.User;
import com.enigma.hakaton.model.enums.UserStatus;
import com.enigma.hakaton.model.request.UserIdJsonRequest;
import com.enigma.hakaton.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

import static java.util.stream.Collectors.groupingBy;
import static org.springframework.http.ResponseEntity.ok;

@RestController
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

    private final UserService userService;

    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/getUsers")
    public Callable<ResponseEntity<Map<UserStatus, List<User>>>> getAllUsers() {
        return () -> ok(userService.getAllUsers().stream().collect(groupingBy(User::getUserStatus)));
    }

    @PostMapping("/approveRegistration")
    public Callable<ResponseEntity<Boolean>> approveUserRegistration(@RequestBody UserIdJsonRequest request) {
        return () -> ok(userService.approveUserRegistration(request));
    }

    @PostMapping("/blockUser")
    public Callable<ResponseEntity<?>> blockUser(@RequestBody UserIdJsonRequest request) {
        return () -> ok(userService.blockUser(request));
    }

    @PostMapping("/unblockUser")
    public Callable<ResponseEntity<?>> unblockUser(@RequestBody UserIdJsonRequest request) {
        return () -> ok(userService.unblockUser(request));
    }
}
