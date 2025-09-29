package com.businessbrasil.api.api_cadastro_usuarios.domain.user.user_validator;

import com.businessbrasil.api.api_cadastro_usuarios.domain._shared.validator.Validator;
import com.businessbrasil.api.api_cadastro_usuarios.domain.user.User;

public class UserValidator extends Validator<User> {

    protected UserValidator(User entity) {
        super(entity);
    }

    public static UserValidator create(User entity) {
        return new UserValidator(entity);
    }

    private static final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
    private static final int MIN_NAME_LENGTH = 2;
    private static final int MAX_EMAIL_LENGTH = 255;

    @Override
    protected void validateEntity(User user) {
        if (user.getName() == null || user.getName().isEmpty() || user.getName().length() < MIN_NAME_LENGTH) {
            notification.addError("Name is required and must be at least 2 characters long");
        }
        if (user.getEmail() == null || user.getEmail().isEmpty() || user.getEmail().length() > MAX_EMAIL_LENGTH) {
            notification.addError("Email is required and must be less than 255 characters long");

        }
        if (!user.getEmail().matches(EMAIL_REGEX)) {
            notification.addError("Email is not valid");
        }
        if (user.getEmail().length() > MAX_EMAIL_LENGTH) {
            notification.addError("Email must be less than 255 characters long");
        }
        if (user.getEmail().length() < MIN_NAME_LENGTH) {
            notification.addError("Email must be at least 2 characters long");
        }

    }
}