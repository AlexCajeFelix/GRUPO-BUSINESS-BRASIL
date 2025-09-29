package com.businessbrasil.api.api_cadastro_usuarios.service.validator._shared;

import java.util.ArrayList;
import java.util.List;

public class ServiceNotification {

    private final List<String> errors;

    public ServiceNotification() {
        this.errors = new ArrayList<>();
    }

    public ServiceNotification(ServiceNotification other) {
        this.errors = new ArrayList<>(other.errors);
    }

    public void addError(String error) {
        if (error != null && !error.trim().isEmpty()) {
            errors.add(error);
        }
    }

    public void addError(String field, String message) {
        addError(field + ": " + message);
    }

    public boolean hasErrors() {
        return !errors.isEmpty();
    }

    public List<String> getErrors() {
        return new ArrayList<>(errors);
    }

    public String getErrorsAsString() {
        return String.join(", ", errors);
    }

    public void clear() {
        errors.clear();
    }

    public static ServiceNotification create() {
        return new ServiceNotification();
    }

}
