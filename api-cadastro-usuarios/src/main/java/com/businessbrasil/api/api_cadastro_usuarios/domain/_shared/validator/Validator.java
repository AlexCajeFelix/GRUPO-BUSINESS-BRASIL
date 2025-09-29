package com.businessbrasil.api.api_cadastro_usuarios.domain._shared.validator;

public abstract class Validator<T> implements Validate<T> {

    protected final Notification notification;
    protected final T entity;

    protected Validator(T entity) {
        this.notification = Notification.create();
        this.entity = entity;
    }

    @Override
    public Notification validate(T entity) {
        notification.clear();
        validateEntity(entity);
        return notification;
    }

    protected abstract void validateEntity(T entity);

}
