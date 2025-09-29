package com.businessbrasil.api.api_cadastro_usuarios.domain._shared.exception.service_exception;

import com.businessbrasil.api.api_cadastro_usuarios.domain._shared.exception.NoSTackTraceException;

public class UserCreatedNotFoundException extends NoSTackTraceException {

    private UserCreatedNotFoundException(String message) {
        super(message);
    }

    private UserCreatedNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public static UserCreatedNotFoundException create(String message) {
        return new UserCreatedNotFoundException(message);
    }

    public static UserCreatedNotFoundException create(String message, Throwable cause) {
        return new UserCreatedNotFoundException(message, cause);
    }
}
