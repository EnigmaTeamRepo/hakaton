package com.enigma.hakaton.controller.rest;

import com.enigma.hakaton.exception.BillAlreadyExistsException;
import com.enigma.hakaton.exception.LoginAlreadyExistException;
import com.enigma.hakaton.exception.PasswordNonMatchException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static org.springframework.http.ResponseEntity.badRequest;
import static org.springframework.http.ResponseEntity.internalServerError;

@RestControllerAdvice
public class ExceptionController {

    private final Logger log = LoggerFactory.getLogger("com.enigma.hakaton.controller.rest");

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> exceptionHandling(Exception e) {
        log.error(e.getLocalizedMessage(), e);
        return internalServerError().build();
    }

    @ExceptionHandler(LoginAlreadyExistException.class)
    public ResponseEntity<?> loginAlreadyExistExceptionHandling(LoginAlreadyExistException e) {
        log.error(e.getLocalizedMessage(), e);
        return badRequest().body("User wilt this login already registered");
    }

    @ExceptionHandler(PasswordNonMatchException.class)
    public ResponseEntity<?> passwordNonMatchExceptionHandling(PasswordNonMatchException e) {
        log.error(e.getLocalizedMessage(), e);
        return badRequest().body("Password non match");
    }

    @ExceptionHandler(BillAlreadyExistsException.class)
    public ResponseEntity<?> billAlreadyExistsExceptionHandling(BillAlreadyExistsException e) {
        log.error(e.getLocalizedMessage(), e);
        return badRequest().body("Bill wilt this currency already exists");
    }
}
