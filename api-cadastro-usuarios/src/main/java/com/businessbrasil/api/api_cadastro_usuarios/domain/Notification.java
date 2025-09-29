package main.java.com.businessbrasil.api.api_cadastro_usuarios.domain;

import java.util.ArrayList;
import java.util.List;

public class Notification {

    private final List<String> errors = new ArrayList<>();

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

    public static Notification withError(String error) {
        Notification notification = new Notification();
        notification.addError(error);
        return notification;
    }

    public static Notification withError(String field, String message) {
        Notification notification = new Notification();
        notification.addError(field, message);
        return notification;
    }
}
