package com.businessbrasil.api.api_cadastro_usuarios.domain._shared.validator;

public interface Validate<T> {
    Notification validate(T entity);
}
