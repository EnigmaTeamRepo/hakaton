package com.enigma.hakaton.controller.rest;

import com.enigma.hakaton.model.User;
import com.enigma.hakaton.model.dto.UserDTO;
import com.enigma.hakaton.service.OperationService;
import com.enigma.hakaton.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.Callable;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@PreAuthorize("hasRole('USER')")
public class UserController {

    private final Logger log = LoggerFactory.getLogger("com.enigma.hakaton.controller.rest");

    private final UserService userService;
    private final OperationService operationService;

    @Autowired
    public UserController(UserService userService, OperationService operationService) {
        this.userService = userService;
        this.operationService = operationService;
    }

    @GetMapping("/profile")
    public Callable<ResponseEntity<UserDTO>> getUserDTO(@AuthenticationPrincipal User user) {
        return () -> ok(userService.getBtoByUserId(user.getId()));
    }

    @GetMapping("/{valute}/buy")
    public Callable<ResponseEntity<Long>> buyValute(@PathVariable("valute") Integer code,
                                                 @RequestParam("lots") Long lots,
                                                    @AuthenticationPrincipal User user) {
        return () -> ok(operationService.buy(code, lots, user.getId()));
    }

    @GetMapping("/{valute}/sell")
    public Callable<ResponseEntity<?>> sellValute(@PathVariable("valute") Integer code,
                                                 @RequestParam("lots") Long lots,
                                                  @AuthenticationPrincipal User user) {
        return () -> ok(operationService.sell(code, lots, user.getId()));
    }

    @GetMapping("/getOperationHistory")
    public Callable<ResponseEntity<?>> getOperationHistory(@AuthenticationPrincipal User user) {
        return () -> ok(operationService.getOperationDtoByUserId(user.getId()));
    }

    @GetMapping("/createNewBill")
    public Callable<ResponseEntity<?>> createNewBill(@RequestParam("code") Integer code) {
        return () -> ok().build();
    }
}
