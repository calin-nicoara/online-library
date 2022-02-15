package com.example.onlinelibrary.configs;

import com.example.onlinelibrary.exceptions.AuthenticationException;
import com.example.onlinelibrary.exceptions.NotFoundException;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import lombok.extern.slf4j.Slf4j;

@RestControllerAdvice
@Slf4j
public class GeneralControllerAdvice {
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> handleRuntimeException(RuntimeException runtimeException) {
        log.error("Error occurred: ", runtimeException);

        return ResponseEntity.internalServerError().body(runtimeException.getMessage());
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<String> handleEntityNotFound() {
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<String> handleAuthenticationException(AuthenticationException authenticationException) {
        return ResponseEntity.badRequest().body(authenticationException.getMessage());
    }
}
