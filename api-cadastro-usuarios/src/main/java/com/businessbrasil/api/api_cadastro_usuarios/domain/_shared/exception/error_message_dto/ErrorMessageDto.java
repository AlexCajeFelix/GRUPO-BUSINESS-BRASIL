package com.businessbrasil.api.api_cadastro_usuarios.domain._shared.exception.error_message_dto;

import java.time.Instant;
import java.util.Map;

public record ErrorMessageDto(
        String type,
        String title,
        int status,
        String detail,
        String instance,
        Instant timestamp,
        Map<String, Object> extensions) {

    public ErrorMessageDto(String type, String title, int status, String detail, String instance) {
        this(type, title, status, detail, instance, Instant.now(), Map.of());
    }

    public ErrorMessageDto(String type, String title, int status, String detail, String instance,
            Map<String, Object> extensions) {
        this(type, title, status, detail, instance, Instant.now(), extensions);
    }

    public static ErrorMessageDto create(String type, String title, int status, String detail, String instance) {
        return new ErrorMessageDto(type, title, status, detail, instance);
    }

    public static ErrorMessageDto create(String type, String title, int status, String detail, String instance,
            Map<String, Object> extensions) {
        return new ErrorMessageDto(type, title, status, detail, instance, extensions);
    }
}
