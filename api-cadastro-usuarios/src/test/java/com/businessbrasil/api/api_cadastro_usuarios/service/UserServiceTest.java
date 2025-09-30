package com.businessbrasil.api.api_cadastro_usuarios.service;

import com.businessbrasil.api.api_cadastro_usuarios.domain.user.User;
import com.businessbrasil.api.api_cadastro_usuarios.repository.UserRepository;
import com.businessbrasil.api.api_cadastro_usuarios.service.dto.CreateUserRequest;
import com.businessbrasil.api.api_cadastro_usuarios.service.dto.UpdateUserRequest;
import com.businessbrasil.api.api_cadastro_usuarios.service.dto.UserResponse;
import com.businessbrasil.api.api_cadastro_usuarios.service.dto.UserSearchRequest;
import com.businessbrasil.api.api_cadastro_usuarios.service.validator.user_service_validator.create_user_validator.CreateUserValidation;
import com.businessbrasil.api.api_cadastro_usuarios.domain._shared.exception.service_exception.UserCreatedNotFoundException;
import com.businessbrasil.api.api_cadastro_usuarios.service.validator._shared.ServiceNotification;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private CreateUserValidation createUserValidation;

    @InjectMocks
    private UserService userService;

    private User user;
    private CreateUserRequest createUserRequest;
    private UpdateUserRequest updateUserRequest;
    private UserSearchRequest userSearchRequest;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setId(UUID.randomUUID());
        user.setName("João Silva");
        user.setEmail("joao@email.com");
        user.setIsActive(true);
        user.setCreatedAt(Instant.now());
        user.setUpdatedAt(Instant.now());
        user.setDeletedAt(null);

        createUserRequest = new CreateUserRequest("João Silva", "joao@email.com");
        updateUserRequest = new UpdateUserRequest("João Silva Santos", "joao.santos@email.com");
        userSearchRequest = new UserSearchRequest("João", null, null, 0, 10, "name", "asc");
    }

    @Test
    void createUser_ShouldReturnUserResponse_WhenValidRequest() {
        ServiceNotification notification = mock(ServiceNotification.class);
        when(notification.hasErrors()).thenReturn(false);
        when(createUserValidation.validate(any(CreateUserRequest.class))).thenReturn(notification);
        when(userRepository.save(any(User.class))).thenReturn(user);

        UserResponse result = userService.createUser(createUserRequest);

        assertNotNull(result);
        assertEquals(user.getId(), result.getId());
        assertEquals(user.getName(), result.getName());
        assertEquals(user.getEmail(), result.getEmail());
        verify(userRepository).save(any(User.class));
    }

    @Test
    void createUser_ShouldThrowException_WhenValidationFails() {
        ServiceNotification notification = mock(ServiceNotification.class);
        when(notification.hasErrors()).thenReturn(true);
        when(notification.getErrorsAsString()).thenReturn("Email já está em uso");
        when(createUserValidation.validate(any(CreateUserRequest.class))).thenReturn(notification);

        assertThrows(UserCreatedNotFoundException.class, () -> {
            userService.createUser(createUserRequest);
        });
    }

    @Test
    void getUserById_ShouldReturnUserResponse_WhenUserExists() {
        when(userRepository.findActiveById(any(UUID.class))).thenReturn(Optional.of(user));

        UserResponse result = userService.getUserById(user.getId());

        assertNotNull(result);
        assertEquals(user.getId(), result.getId());
        assertEquals(user.getName(), result.getName());
        assertEquals(user.getEmail(), result.getEmail());
    }

    @Test
    void getUserById_ShouldThrowException_WhenUserNotFound() {
        when(userRepository.findActiveById(any(UUID.class))).thenReturn(Optional.empty());

        assertThrows(UserCreatedNotFoundException.class, () -> {
            userService.getUserById(user.getId());
        });
    }

    @Test
    void updateUser_ShouldReturnUpdatedUserResponse_WhenUserExists() {
        when(userRepository.findActiveById(any(UUID.class))).thenReturn(Optional.of(user));
        when(userRepository.save(any(User.class))).thenReturn(user);

        UserResponse result = userService.updateUser(user.getId(), updateUserRequest);

        assertNotNull(result);
        assertEquals(user.getId(), result.getId());
        verify(userRepository).save(any(User.class));
    }

    @Test
    void updateUser_ShouldThrowException_WhenUserNotFound() {
        when(userRepository.findActiveById(any(UUID.class))).thenReturn(Optional.empty());

        assertThrows(UserCreatedNotFoundException.class, () -> {
            userService.updateUser(user.getId(), updateUserRequest);
        });
    }

    @Test
    void deleteUser_ShouldCallSoftDelete_WhenUserExists() {
        when(userRepository.findActiveById(any(UUID.class))).thenReturn(Optional.of(user));

        userService.deleteUser(user.getId());

        verify(userRepository).softDeleteById(user.getId());
    }

    @Test
    void deleteUser_ShouldThrowException_WhenUserNotFound() {
        when(userRepository.findActiveById(any(UUID.class))).thenReturn(Optional.empty());

        assertThrows(UserCreatedNotFoundException.class, () -> {
            userService.deleteUser(user.getId());
        });
    }

    @Test
    void getUsers_ShouldReturnPageOfUsers() {
        Page<User> userPage = new PageImpl<>(List.of(user));
        when(userRepository.findActiveUsers(any(Pageable.class))).thenReturn(userPage);

        Page<User> result = userService.getUsers(PageRequest.of(0, 10));

        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        assertEquals(user.getId(), result.getContent().get(0).getId());
    }

    @Test
    void searchUsers_ShouldReturnFilteredUsers_WhenSearchingByName() {
        Page<User> userPage = new PageImpl<>(List.of(user));
        when(userRepository.findByNameContainingIgnoreCase(anyString(), any(Pageable.class))).thenReturn(userPage);

        Page<User> result = userService.searchUsers(userSearchRequest);

        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        verify(userRepository).findByNameContainingIgnoreCase(eq("João"), any(Pageable.class));
    }

    @Test
    void searchUsers_ShouldReturnFilteredUsers_WhenSearchingByEmail() {
        userSearchRequest.setName(null);
        userSearchRequest.setEmail("joao");
        Page<User> userPage = new PageImpl<>(List.of(user));
        when(userRepository.findByEmailContainingIgnoreCase(anyString(), any(Pageable.class))).thenReturn(userPage);

        Page<User> result = userService.searchUsers(userSearchRequest);

        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        verify(userRepository).findByEmailContainingIgnoreCase(eq("joao"), any(Pageable.class));
    }

    @Test
    void searchUsers_ShouldReturnAllActiveUsers_WhenNoSearchCriteria() {
        userSearchRequest.setName(null);
        userSearchRequest.setEmail(null);
        Page<User> userPage = new PageImpl<>(List.of(user));
        when(userRepository.findActiveUsers(any(Pageable.class))).thenReturn(userPage);

        Page<User> result = userService.searchUsers(userSearchRequest);

        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        verify(userRepository).findActiveUsers(any(Pageable.class));
    }
}
