package com.businessbrasil.api.api_cadastro_usuarios.controller;

import com.businessbrasil.api.api_cadastro_usuarios.service.dto.CreateUserRequest;
import com.businessbrasil.api.api_cadastro_usuarios.service.dto.UserResponse;
import com.businessbrasil.api.api_cadastro_usuarios.service.dto.UpdateUserRequest;
import com.businessbrasil.api.api_cadastro_usuarios.service.dto.UserSearchRequest;
import com.businessbrasil.api.api_cadastro_usuarios.domain.user.User;
import com.businessbrasil.api.api_cadastro_usuarios.service.UserService;
import jakarta.validation.Valid;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.UUID;

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

    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@PathVariable UUID id) {
        UserResponse user = userService.getUserById(id);
        return ResponseEntity.ok(user);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(@PathVariable UUID id, @Valid @RequestBody UpdateUserRequest request) {
        UserResponse user = userService.updateUser(id, request);
        return ResponseEntity.ok(user);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable UUID id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/search")
    public ResponseEntity<?> searchUsers(@Valid @RequestBody UserSearchRequest request) {
        Page<User> users = userService.searchUsers(request);
        return ResponseEntity.ok(users);
    }

}
