package com.businessbrasil.api.api_cadastro_usuarios.exception;

import com.businessbrasil.api.api_cadastro_usuarios.domain.DomainException;
import com.businessbrasil.api.api_cadastro_usuarios.domain.Notification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(DomainException.class)
    public ResponseEntity<Map<String, Object>> handleDomainException(DomainException ex) {
        Map<String, Object> response = new HashMap<>();
        response.put("timestamp", LocalDateTime.now());
        response.put("status", HttpStatus.BAD_REQUEST.value());
        response.put("error", "Domain Validation Error");
        response.put("message", ex.getNotification().getErrorsAsString());
        response.put("errors", ex.getNotification().getErrors());
        response.put("path", "/api/v1/users");
        
        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidationException(MethodArgumentNotValidException ex) {
        Notification notification = Notification.create();
        
        ex.getBindingResult().getFieldErrors().forEach(error -> {
            notification.addError(error.getField(), error.getDefaultMessage());
        });

        Map<String, Object> response = new HashMap<>();
        response.put("timestamp", LocalDateTime.now());
        response.put("status", HttpStatus.BAD_REQUEST.value());
        response.put("error", "Validation Error");
        response.put("message", notification.getErrorsAsString());
        response.put("errors", notification.getErrors());
        response.put("path", "/api/v1/users");
        
        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleGenericException(Exception ex) {
        Map<String, Object> response = new HashMap<>();
        response.put("timestamp", LocalDateTime.now());
        response.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
        response.put("error", "Internal Server Error");
        response.put("message", "Ocorreu um erro interno no servidor");
        response.put("path", "/api/v1/users");
        
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
}
