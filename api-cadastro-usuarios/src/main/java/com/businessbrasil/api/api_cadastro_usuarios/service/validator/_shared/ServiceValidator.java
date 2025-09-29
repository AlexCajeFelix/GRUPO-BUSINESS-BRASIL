package com.businessbrasil.api.api_cadastro_usuarios.service.validator._shared;

public abstract class ServiceValidator<T> implements ServiceValidate<T> {

    protected final ServiceNotification notification;

    protected ServiceValidator() {
        this.notification = ServiceNotification.create();

    }

    protected ServiceValidator(T request) {
        this.notification = ServiceNotification.create();

    }

    @Override
    public ServiceNotification validate(T request) {
        notification.clear();
        validateRequest(request);
        return notification;
    }

    protected abstract void validateRequest(T request);

}
