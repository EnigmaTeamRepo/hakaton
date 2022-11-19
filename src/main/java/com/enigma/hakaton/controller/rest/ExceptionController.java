package com.enigma.hakaton.controller.rest;

import com.enigma.hakaton.exception.LoginAlreadyExistException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionController {

    private final Logger log = LoggerFactory.getLogger("");

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> exceptionHandling(Exception e) {
        log.error(e.getLocalizedMessage(), e);
        return ResponseEntity.internalServerError().build();
    }

    @ExceptionHandler(LoginAlreadyExistException.class)
    public ResponseEntity<?> loginAlreadyExistExceptionHandling(LoginAlreadyExistException e) {
        log.error(e.getLocalizedMessage(), e);
        return ResponseEntity.badRequest().body("User wilt this login already registered");
    }
}
