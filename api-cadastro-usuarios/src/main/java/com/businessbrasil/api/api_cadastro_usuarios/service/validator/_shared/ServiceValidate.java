package com.businessbrasil.api.api_cadastro_usuarios.service.validator._shared;

public interface ServiceValidate<T> {
    ServiceNotification validate(T request);
}
