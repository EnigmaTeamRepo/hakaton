package com.enigma.hakaton.controller.rest;

import com.enigma.hakaton.model.dto.UserDTO;
import com.enigma.hakaton.model.enums.UserStatus;
import com.enigma.hakaton.model.request.UserIdJsonRequest;
import com.enigma.hakaton.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.Callable;

import static java.util.stream.Collectors.groupingBy;
import static org.springframework.http.ResponseEntity.ok;

@RestController
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

    private final Logger log = LoggerFactory.getLogger("com.enigma.hakaton.controller.rest");

    private final UserService userService;

    @Autowired
    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/getUsersByStatus")
    public Callable<ResponseEntity<List<UserDTO>>> getAllUsersByStatus(@RequestParam("status") UserStatus status) {
        return () -> ok(userService.getAllUsers().stream().collect(groupingBy(UserDTO::getUserStatus)).get(status));
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
