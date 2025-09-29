package com.businessbrasil.api.api_cadastro_usuarios.controller;

import com.businessbrasil.api.api_cadastro_usuarios.service.dto.CreateUserRequest;
import com.businessbrasil.api.api_cadastro_usuarios.service.dto.UserResponse;
import com.businessbrasil.api.api_cadastro_usuarios.service.UserService;
import jakarta.validation.Valid;

import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/users")
@CrossOrigin(origins = "*")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<?> createUser(@Valid @RequestBody CreateUserRequest request,
            UriComponentsBuilder uriComponentsBuilder) {
        UserResponse user = userService.createUser(request);
        var uri = uriComponentsBuilder.path("/users/{id}").buildAndExpand(user.getId()).toUri();
        return ResponseEntity.created(uri).body(user);
    }

    @GetMapping
    public ResponseEntity<?> getUsers(Pageable pageable) {
        return ResponseEntity.ok(userService.getUsers(pageable));
    }

}
