package com.businessbrasil.api.api_cadastro_usuarios.service;

import com.businessbrasil.api.api_cadastro_usuarios.service.dto.*;
import com.businessbrasil.api.api_cadastro_usuarios.service.validator.user_service_validator.create_user_validator.CreateUserValidation;
import com.businessbrasil.api.api_cadastro_usuarios.service.validator._shared.ServiceNotification;
import com.businessbrasil.api.api_cadastro_usuarios.domain._shared.exception.service_exception.UserCreatedNotFoundException;
import com.businessbrasil.api.api_cadastro_usuarios.domain.user.User;
import com.businessbrasil.api.api_cadastro_usuarios.repository.UserRepository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;
import java.util.Optional;

@Service
@Transactional
public class UserService {

    private final UserRepository userRepository;
    private final CreateUserValidation createUserValidation;

    public UserService(UserRepository userRepository,
            CreateUserValidation createUserValidation) {

        this.userRepository = userRepository;
        this.createUserValidation = createUserValidation;
    }

    @Transactional
    public UserResponse createUser(CreateUserRequest request) {
        validateCreateUserRequest(request);
        User user = User.create(request.getName(), request.getEmail());
        User savedUser = userRepository.save(user);
        return UserResponse.from(savedUser);
    }

    private void validateCreateUserRequest(CreateUserRequest request) {
        ServiceNotification notification = createUserValidation.validate(request);
        if (notification.hasErrors()) {
            throw UserCreatedNotFoundException.create(notification.getErrorsAsString());
        }
    }

    public Page<User> getUsers(Pageable pageable) {
        return userRepository.findActiveUsers(pageable);
    }

    public UserResponse getUserById(UUID id) {
        Optional<User> user = userRepository.findActiveById(id);
        if (user.isEmpty()) {
            throw UserCreatedNotFoundException.create("Usuário não encontrado");
        }
        return UserResponse.from(user.get());
    }

    @Transactional
    public UserResponse updateUser(UUID id, UpdateUserRequest request) {
        Optional<User> userOpt = userRepository.findActiveById(id);
        if (userOpt.isEmpty()) {
            throw UserCreatedNotFoundException.create("Usuário não encontrado");
        }

        User user = userOpt.get();
        user.update(request.getName(), request.getEmail());
        User savedUser = userRepository.save(user);
        return UserResponse.from(savedUser);
    }

    @Transactional
    public void deleteUser(UUID id) {
        Optional<User> userOpt = userRepository.findActiveById(id);
        if (userOpt.isEmpty()) {
            throw UserCreatedNotFoundException.create("Usuário não encontrado");
        }

        userRepository.softDeleteById(id);
    }

    public Page<User> searchUsers(UserSearchRequest request) {
        Sort sort = Sort.by(
                "desc".equalsIgnoreCase(request.getSortDirection())
                        ? Sort.Direction.DESC
                        : Sort.Direction.ASC,
                request.getSortBy());

        Pageable pageable = org.springframework.data.domain.PageRequest.of(
                request.getPage(),
                request.getSize(),
                sort);

        if (request.getName() != null && !request.getName().isEmpty()) {
            return userRepository.findByNameContainingIgnoreCase(request.getName(), pageable);
        }

        if (request.getEmail() != null && !request.getEmail().isEmpty()) {
            return userRepository.findByEmailContainingIgnoreCase(request.getEmail(), pageable);
        }

        return userRepository.findActiveUsers(pageable);
    }
}