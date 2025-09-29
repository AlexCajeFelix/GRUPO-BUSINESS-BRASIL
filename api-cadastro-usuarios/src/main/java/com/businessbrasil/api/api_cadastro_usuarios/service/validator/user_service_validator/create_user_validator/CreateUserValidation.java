package com.businessbrasil.api.api_cadastro_usuarios.service.validator.user_service_validator.create_user_validator;

import com.businessbrasil.api.api_cadastro_usuarios.service.validator._shared.ServiceValidator;

import org.springframework.stereotype.Component;

import com.businessbrasil.api.api_cadastro_usuarios.repository.UserRepository;
import com.businessbrasil.api.api_cadastro_usuarios.service.dto.CreateUserRequest;

@Component
public class CreateUserValidation extends ServiceValidator<CreateUserRequest> {

    private final UserRepository userRepository;

    public CreateUserValidation(UserRepository userRepository) {
        super();
        this.userRepository = userRepository;
    }

    @Override
    protected void validateRequest(CreateUserRequest request) {
        validateBusinessRules(request);
    }

    private void validateBusinessRules(CreateUserRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            notification.addError("Email já está em uso");
        }
    }

}
