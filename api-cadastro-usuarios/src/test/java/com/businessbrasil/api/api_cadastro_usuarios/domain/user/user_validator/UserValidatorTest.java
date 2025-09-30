package com.businessbrasil.api.api_cadastro_usuarios.domain.user.user_validator;

import com.businessbrasil.api.api_cadastro_usuarios.domain._shared.validator.Notification;
import com.businessbrasil.api.api_cadastro_usuarios.domain.user.User;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserValidatorTest {

    private UserValidator userValidator;
    private User user;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setName("João Silva");
        user.setEmail("joao@email.com");
        userValidator = UserValidator.create(user);
    }

    @Test
    void validate_ShouldReturnNoErrors_WhenUserIsValid() {
        Notification notification = userValidator.validate(user);

        assertFalse(notification.hasErrors());
    }

    @Test
    void validate_ShouldReturnError_WhenNameIsNull() {
        user.setName(null);

        Notification notification = userValidator.validate(user);

        assertTrue(notification.hasErrors());
        assertTrue(notification.getErrorsAsString().contains("Name is required"));
    }

    @Test
    void validate_ShouldReturnError_WhenNameIsEmpty() {
        user.setName("");

        Notification notification = userValidator.validate(user);

        assertTrue(notification.hasErrors());
        assertTrue(notification.getErrorsAsString().contains("Name is required"));
    }

    @Test
    void validate_ShouldReturnError_WhenNameIsTooShort() {
        user.setName("J");

        Notification notification = userValidator.validate(user);

        assertTrue(notification.hasErrors());
        assertTrue(notification.getErrorsAsString().contains("Name is required"));
    }

    @Test
    void validate_ShouldReturnError_WhenEmailIsNull() {
        user.setEmail(null);

        Notification notification = userValidator.validate(user);

        assertTrue(notification.hasErrors());
        assertTrue(notification.getErrorsAsString().contains("Email is required"));
    }

    @Test
    void validate_ShouldReturnError_WhenEmailIsEmpty() {
        user.setEmail("");

        Notification notification = userValidator.validate(user);

        assertTrue(notification.hasErrors());
        assertTrue(notification.getErrorsAsString().contains("Email is required"));
    }

    @Test
    void validate_ShouldReturnError_WhenEmailIsInvalid() {
        user.setEmail("invalid-email");

        Notification notification = userValidator.validate(user);

        assertTrue(notification.hasErrors());
        assertTrue(notification.getErrorsAsString().contains("Email is not valid"));
    }

    @Test
    void validate_ShouldReturnError_WhenEmailIsTooLong() {
        user.setEmail("a".repeat(256) + "@email.com");

        Notification notification = userValidator.validate(user);

        assertTrue(notification.hasErrors());
        assertTrue(notification.getErrorsAsString().contains("Email must be less than 255 characters long"));
    }

    @Test
    void validate_ShouldReturnError_WhenEmailIsTooShort() {
        user.setEmail("a@");

        Notification notification = userValidator.validate(user);

        assertTrue(notification.hasErrors());
        assertTrue(notification.getErrorsAsString().contains("Email must be at least 2 characters long"));
    }

    @Test
    void validate_ShouldReturnMultipleErrors_WhenMultipleFieldsAreInvalid() {
        user.setName("");
        user.setEmail("invalid");

        Notification notification = userValidator.validate(user);

        assertTrue(notification.hasErrors());
        assertTrue(notification.getErrorsAsString().contains("Name is required"));
        assertTrue(notification.getErrorsAsString().contains("Email is not valid"));
    }
}
