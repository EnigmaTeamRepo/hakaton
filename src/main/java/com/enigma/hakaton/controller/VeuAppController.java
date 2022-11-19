package com.enigma.hakaton.controller;

import com.enigma.hakaton.model.request.RegisterRequest;
import com.enigma.hakaton.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.concurrent.Callable;

import static org.springframework.http.ResponseEntity.ok;

@Controller
@RequestMapping("/")
public class VeuAppController {

    private final UserService userService;

    @Autowired
    public VeuAppController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public Callable<ModelAndView> returnVueApp(ModelAndView modelAndView) {
        return () -> {
            modelAndView.setViewName("index");
            return modelAndView;
        };
    }

    @PostMapping("registration")
    @ResponseBody
    public Callable<ResponseEntity<Boolean>> registration(@RequestBody RegisterRequest registerRequest) {
        return () -> ok(userService.registerNew(registerRequest));
    }
}
