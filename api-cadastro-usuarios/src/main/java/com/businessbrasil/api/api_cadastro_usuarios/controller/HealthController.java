package com.businessbrasil.api.api_cadastro_usuarios.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1")
public class HealthController {

    @GetMapping("/health")
    public Map<String, Object> health() {
        Map<String, Object> response = new HashMap<>();
        response.put("status", "UP");
        response.put("message", "API de Cadastro de Usuários funcionando");
        response.put("timestamp", LocalDateTime.now());
        response.put("version", "1.0.0");
        response.put("developer", "Alex Caje Felix");
        return response;
    }

    @GetMapping("/")
    public Map<String, Object> root() {
        Map<String, Object> response = new HashMap<>();
        response.put("message", "API de Cadastro de Usuários - Grupo Business Brasil");
        response.put("version", "1.0.0");
        response.put("endpoints", new String[]{
            "GET /api/v1/health - Health check",
            "GET /api/v1/users - Listar usuários",
            "POST /api/v1/users - Criar usuário",
            "GET /api/v1/users/{id} - Buscar usuário por ID"
        });
        return response;
    }
}
