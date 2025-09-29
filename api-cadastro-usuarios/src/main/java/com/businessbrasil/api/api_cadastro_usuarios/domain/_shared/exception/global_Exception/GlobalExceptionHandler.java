package com.businessbrasil.api.api_cadastro_usuarios.domain._shared.exception.global_Exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import com.businessbrasil.api.api_cadastro_usuarios.domain._shared.validator.Notification;
import com.businessbrasil.api.api_cadastro_usuarios.domain._shared.exception.error_message_dto.ErrorMessageDto;
import com.businessbrasil.api.api_cadastro_usuarios.domain._shared.exception.domain_exception.UserDomainException;
import com.businessbrasil.api.api_cadastro_usuarios.domain._shared.exception.service_exception.UserCreatedNotFoundException;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorMessageDto> handleValidationException(MethodArgumentNotValidException ex,
            WebRequest request) {
        Notification notification = Notification.create();

        ex.getBindingResult().getFieldErrors().forEach(error -> {
            notification.addError(error.getField(), error.getDefaultMessage());
        });

        Map<String, Object> extensions = new HashMap<>();
        extensions.put("errors", notification.getErrors());
        extensions.put("fieldErrors", ex.getBindingResult().getFieldErrors());

        ErrorMessageDto errorResponse = ErrorMessageDto.create(
                "https://api.businessbrasil.com/problems/validation-error",
                "Validation Error",
                HttpStatus.BAD_REQUEST.value(),
                notification.getErrorsAsString(),
                request.getDescription(false),
                extensions);

        return ResponseEntity.badRequest().body(errorResponse);
    }

    @ExceptionHandler(UserDomainException.class)
    public ResponseEntity<ErrorMessageDto> handleUserDomainException(UserDomainException ex, WebRequest request) {
        ErrorMessageDto errorResponse = ErrorMessageDto.create(
                "https://api.businessbrasil.com/problems/domain-error",
                "Domain Validation Error",
                HttpStatus.BAD_REQUEST.value(),
                ex.getMessage(),
                request.getDescription(false));

        return ResponseEntity.badRequest().body(errorResponse);
    }

    @ExceptionHandler(UserCreatedNotFoundException.class)
    public ResponseEntity<ErrorMessageDto> handleUserNotFoundException(UserCreatedNotFoundException ex,
            WebRequest request) {
        ErrorMessageDto errorResponse = ErrorMessageDto.create(
                "https://api.businessbrasil.com/problems/user-not-found",
                "User Not Found",
                HttpStatus.NOT_FOUND.value(),
                ex.getMessage(),
                request.getDescription(false));

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorMessageDto> handleGenericException(Exception ex, WebRequest request) {
        ErrorMessageDto errorResponse = ErrorMessageDto.create(
                "https://api.businessbrasil.com/problems/internal-server-error",
                "Internal Server Error",
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Ocorreu um erro interno no servidor",
                request.getDescription(false));

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }
}
