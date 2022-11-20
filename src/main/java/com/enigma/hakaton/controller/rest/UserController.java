package com.enigma.hakaton.controller.rest;

import com.enigma.hakaton.model.User;
import com.enigma.hakaton.model.dto.UserDTO;
import com.enigma.hakaton.model.enums.Currency;
import com.enigma.hakaton.service.OperationService;
import com.enigma.hakaton.service.UserService;
import com.enigma.hakaton.service.ValuteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.concurrent.Callable;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@PreAuthorize("hasRole('USER')")
public class UserController {

    private final Logger log = LoggerFactory.getLogger("com.enigma.hakaton.controller.rest");

    private final UserService userService;
    private final OperationService operationService;
    private final ValuteService valuteService;

    @Autowired
    public UserController(UserService userService, OperationService operationService, ValuteService valuteService) {
        this.userService = userService;
        this.operationService = operationService;
        this.valuteService = valuteService;
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

    @GetMapping("/payIn")
    public Callable<ResponseEntity<?>> payInOperation(@AuthenticationPrincipal User user,
                                                      @RequestParam("amount") Long amount) {
        return () -> ok(operationService.payIn(user.getId(), amount));
    }

    @GetMapping("/payOut")
    public Callable<ResponseEntity<?>> payOutOperation(@AuthenticationPrincipal User user,
                                                      @RequestParam("amount") Long amount) {
        return () -> ok(operationService.payOut(user.getId(), amount));
    }

    @GetMapping("/createNewBill")
    public Callable<ResponseEntity<?>> createNewBill(@RequestParam("code") Integer code) {
        return () -> ok().build();
    }

    @GetMapping("/valuteRates")
    public Callable<ResponseEntity<Map<Currency, Map<String, String>>>> getRates() {
        return () -> ok(valuteService.getValuteRates());
    }
}
