package com.pits.pist_api.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Map<String, String>> handleRuntime(RuntimeException ex) {
        String msg = ex.getMessage();

        HttpStatus status = HttpStatus.BAD_REQUEST;
        if (msg != null && msg.contains("no encontrado")) {
            status = HttpStatus.NOT_FOUND;
        } else if (msg != null && msg.contains("inválid")) {
            status = HttpStatus.UNAUTHORIZED;
        }

        return ResponseEntity.status(status)
                .body(Map.of("message", msg != null ? msg : "Error interno"));
    }
}
