package com.enigma.hakaton.controller;

import com.enigma.hakaton.model.User;
import com.enigma.hakaton.model.dto.UserDTO;
import com.enigma.hakaton.model.request.RegisterRequest;
import com.enigma.hakaton.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.Callable;

import static org.springframework.http.ResponseEntity.ok;

@Controller
@RequestMapping("/")
public class VeuAppController {

    private final Logger log = LoggerFactory.getLogger("com.enigma.hakaton.controller");

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

    @GetMapping("csrf")
    @ResponseBody
    public Callable<ResponseEntity<CsrfToken>> getCsrf(HttpServletRequest request) {
        return () -> ok((CsrfToken) request.getAttribute(CsrfToken.class.getName()));
    }

    @GetMapping("user")
    @ResponseBody
    public Callable<ResponseEntity<UserDTO>> getUserInfo(@AuthenticationPrincipal User user) {
        return () -> ok(userService.getBtoByUserId(user.getId()));
    }

    @PostMapping("registration")
    @ResponseBody
    public Callable<ResponseEntity<Boolean>> registration(@RequestBody RegisterRequest registerRequest) {
        return () -> ok(userService.registerNew(registerRequest));
    }
}
