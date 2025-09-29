package com.businessbrasil.api.api_cadastro_usuarios.domain._shared.validator;

import java.util.ArrayList;
import java.util.List;

public class Notification {

    private final List<String> errors;

    public Notification() {
        this.errors = new ArrayList<>();
    }

    public Notification(Notification other) {
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

    public static Notification create() {
        return new Notification();
    }

}
