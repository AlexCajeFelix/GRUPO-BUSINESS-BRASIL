package com.businessbrasil.api.api_cadastro_usuarios.domain._shared.exception;

public abstract class NoSTackTraceException extends RuntimeException {

    protected NoSTackTraceException(String message) {
        super(message, null, false, false);
    }

    protected NoSTackTraceException(String message, Throwable cause) {
        super(message, cause, false, false);
    }

}
