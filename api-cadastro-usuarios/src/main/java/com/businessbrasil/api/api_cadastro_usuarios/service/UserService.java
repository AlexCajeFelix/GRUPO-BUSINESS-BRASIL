package com.businessbrasil.api.api_cadastro_usuarios.service;

import com.businessbrasil.api.api_cadastro_usuarios.service.dto.*;
import com.businessbrasil.api.api_cadastro_usuarios.service.validator.user_service_validator.create_user_validator.CreateUserValidation;
import com.businessbrasil.api.api_cadastro_usuarios.service.validator._shared.ServiceNotification;
import com.businessbrasil.api.api_cadastro_usuarios.domain._shared.exception.service_exception.UserCreatedNotFoundException;
import com.businessbrasil.api.api_cadastro_usuarios.domain.user.User;
import com.businessbrasil.api.api_cadastro_usuarios.repository.UserRepository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        return userRepository.findAll(pageable);

    }
}