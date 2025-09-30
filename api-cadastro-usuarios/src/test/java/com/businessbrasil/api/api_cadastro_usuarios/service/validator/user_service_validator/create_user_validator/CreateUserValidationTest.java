package com.businessbrasil.api.api_cadastro_usuarios.service.validator.user_service_validator.create_user_validator;

import com.businessbrasil.api.api_cadastro_usuarios.repository.UserRepository;
import com.businessbrasil.api.api_cadastro_usuarios.service.dto.CreateUserRequest;
import com.businessbrasil.api.api_cadastro_usuarios.service.validator._shared.ServiceNotification;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.ArgumentMatchers.anyString;

@ExtendWith(MockitoExtension.class)
class CreateUserValidationTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private CreateUserValidation createUserValidation;

    private CreateUserRequest createUserRequest;

    @BeforeEach
    void setUp() {
        createUserRequest = new CreateUserRequest("João Silva", "joao@email.com");
    }

    @Test
    void validate_ShouldReturnNoErrors_WhenEmailIsNotInUse() {
        when(userRepository.existsByEmail(anyString())).thenReturn(false);

        ServiceNotification notification = createUserValidation.validate(createUserRequest);

        assertFalse(notification.hasErrors());
    }

    @Test
    void validate_ShouldReturnError_WhenEmailIsAlreadyInUse() {
        when(userRepository.existsByEmail(anyString())).thenReturn(true);

        ServiceNotification notification = createUserValidation.validate(createUserRequest);

        assertTrue(notification.hasErrors());
        assertTrue(notification.getErrorsAsString().contains("Email já está em uso"));
    }

    @Test
    void validate_ShouldCallRepositoryWithCorrectEmail() {
        when(userRepository.existsByEmail(anyString())).thenReturn(false);

        createUserValidation.validate(createUserRequest);

        verify(userRepository).existsByEmail("joao@email.com");
    }
}
