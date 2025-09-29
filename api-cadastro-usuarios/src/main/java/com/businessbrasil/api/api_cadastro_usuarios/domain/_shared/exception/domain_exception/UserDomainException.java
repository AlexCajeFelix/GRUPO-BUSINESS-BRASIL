package com.businessbrasil.api.api_cadastro_usuarios.domain._shared.exception.domain_exception;

import java.util.List;

import com.businessbrasil.api.api_cadastro_usuarios.domain._shared.exception.NoSTackTraceException;

public class UserDomainException extends NoSTackTraceException {

    protected UserDomainException(String errorMessageDto) {
        super(errorMessageDto);
    }

    protected UserDomainException(String errorMessageDto, Throwable cause) {
        super(errorMessageDto, cause);
    }

    public static UserDomainException create(String errorMessageDto) {
        return new UserDomainException(errorMessageDto);
    }

    public static UserDomainException create(List<String> error) {
        String errorMessage = String.join(", ", error);
        return new UserDomainException(errorMessage);
    }

    public static UserDomainException create(String errorMessageDto, Throwable cause) {
        return new UserDomainException(errorMessageDto, cause);
    }

}
