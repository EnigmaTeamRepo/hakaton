package com.enigma.hakaton.controller;

import com.enigma.hakaton.model.request.RegisterRequest;
import com.enigma.hakaton.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.Callable;

@Controller
@RequestMapping("/")
public class VeuAppController {

    private final UserService userService;

    @Autowired
    public VeuAppController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String returnVueApp(Model model) {
        return "index";
    }

    @PostMapping("registration")
    @ResponseBody
    public Callable<ResponseEntity<Boolean>> registration(@RequestBody RegisterRequest registerRequest) {
        return () -> ResponseEntity.ok(userService.registerNew(registerRequest));
    }
}
