package com.businessbrasil.api.api_cadastro_usuarios.controller;

import com.businessbrasil.api.api_cadastro_usuarios.domain._shared.exception.service_exception.UserCreatedNotFoundException;
import com.businessbrasil.api.api_cadastro_usuarios.domain.user.User;
import com.businessbrasil.api.api_cadastro_usuarios.service.UserService;
import com.businessbrasil.api.api_cadastro_usuarios.service.dto.CreateUserRequest;
import com.businessbrasil.api.api_cadastro_usuarios.service.dto.UpdateUserRequest;
import com.businessbrasil.api.api_cadastro_usuarios.service.dto.UserResponse;
import com.businessbrasil.api.api_cadastro_usuarios.service.dto.UserSearchRequest;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper;

    private User user;
    private UserResponse userResponse;
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

        userResponse = UserResponse.from(user);
        createUserRequest = new CreateUserRequest("João Silva", "joao@email.com");
        updateUserRequest = new UpdateUserRequest("João Silva Santos", "joao.santos@email.com");
        userSearchRequest = new UserSearchRequest("João", null, null, 0, 10, "name", "asc");
    }

    @Test
    void createUser_ShouldReturnCreatedUser_WhenValidRequest() throws Exception {
        when(userService.createUser(any(CreateUserRequest.class))).thenReturn(userResponse);

        mockMvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createUserRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(user.getId().toString()))
                .andExpect(jsonPath("$.name").value(user.getName()))
                .andExpect(jsonPath("$.email").value(user.getEmail()));
    }

    @Test
    void createUser_ShouldReturnBadRequest_WhenInvalidRequest() throws Exception {
        CreateUserRequest invalidRequest = new CreateUserRequest("", "");

        when(userService.createUser(any())).thenThrow(
                UserCreatedNotFoundException.create("Validation Error"));

        mockMvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalidRequest)))
                .andExpect(status().isNotFound());
    }

    @Test
    void getUsers_ShouldReturnPageOfUsers() throws Exception {
        Page<User> userPage = new PageImpl<>(List.of(user));
        when(userService.getUsers(any())).thenReturn(userPage);

        mockMvc.perform(get("/users?page=0&size=10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").isArray())
                .andExpect(jsonPath("$.content[0].id").value(user.getId().toString()))
                .andExpect(jsonPath("$.totalElements").value(1));
    }

    @Test
    void getUserById_ShouldReturnUser_WhenUserExists() throws Exception {
        when(userService.getUserById(any(UUID.class))).thenReturn(userResponse);

        mockMvc.perform(get("/users/{id}", user.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(user.getId().toString()))
                .andExpect(jsonPath("$.name").value(user.getName()))
                .andExpect(jsonPath("$.email").value(user.getEmail()));
    }

    @Test
    void updateUser_ShouldReturnUpdatedUser_WhenUserExists() throws Exception {
        when(userService.updateUser(any(UUID.class), any(UpdateUserRequest.class))).thenReturn(userResponse);

        mockMvc.perform(put("/users/{id}", user.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updateUserRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(user.getId().toString()))
                .andExpect(jsonPath("$.name").value(user.getName()))
                .andExpect(jsonPath("$.email").value(user.getEmail()));
    }

    @Test
    void updateUser_ShouldReturnBadRequest_WhenInvalidRequest() throws Exception {
        UpdateUserRequest invalidRequest = new UpdateUserRequest("", "");

        when(userService.updateUser(any(), any())).thenThrow(
                UserCreatedNotFoundException.create("Validation Error"));

        mockMvc.perform(put("/users/{id}", user.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalidRequest)))
                .andExpect(status().isNotFound());
    }

    @Test
    void deleteUser_ShouldReturnNoContent_WhenUserExists() throws Exception {
        mockMvc.perform(delete("/users/{id}", user.getId()))
                .andExpect(status().isNoContent());
    }

    @Test
    void searchUsers_ShouldReturnFilteredUsers() throws Exception {
        Page<User> userPage = new PageImpl<>(List.of(user));
        when(userService.searchUsers(any(UserSearchRequest.class))).thenReturn(userPage);

        mockMvc.perform(post("/users/search")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userSearchRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").isArray())
                .andExpect(jsonPath("$.content[0].id").value(user.getId().toString()))
                .andExpect(jsonPath("$.totalElements").value(1));
    }

    @Test
    void searchUsers_ShouldReturnBadRequest_WhenInvalidRequest() throws Exception {
        UserSearchRequest invalidRequest = new UserSearchRequest();

        mockMvc.perform(post("/users/search")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalidRequest)))
                .andExpect(status().isOk());
    }
}
