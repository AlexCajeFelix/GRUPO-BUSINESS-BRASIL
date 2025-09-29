package com.businessbrasil.api.api_cadastro_usuarios.domain;

public class DomainException extends RuntimeException {

    private final Notification notification;

    public DomainException(Notification notification) {
        super(notification.getErrorsAsString());
        this.notification = notification;
    }

    public DomainException(String message) {
        super(message);
        this.notification = Notification.withError(message);
    }

    public Notification getNotification() {
        return notification;
    }

    public static DomainException create(Notification notification) {
        return new DomainException(notification);
    }

    public static DomainException create(String message) {
        return new DomainException(message);
    }
}
