package com.businessbrasil.api.api_cadastro_usuarios.domain.user;

import java.time.Instant;
import java.util.UUID;

import com.businessbrasil.api.api_cadastro_usuarios.domain._shared.exception.domain_exception.UserDomainException;
import com.businessbrasil.api.api_cadastro_usuarios.domain._shared.validator.Notification;
import com.businessbrasil.api.api_cadastro_usuarios.domain.user.user_validator.UserValidator;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "users")
public class User {

    @Id
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "name", nullable = false, length = 255)
    private String name;

    @Column(name = "email", nullable = false, unique = true, length = 255)
    private String email;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive = true;

    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    @Column(name = "updated_at")
    private Instant updatedAt;

    @Column(name = "deleted_at")
    private Instant deletedAt;

    public User() {
    }

    private User(String name, String email) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.email = email;
        this.isActive = true;
        this.createdAt = Instant.now();
        this.updatedAt = Instant.now();
        this.deletedAt = null;
    }

    public static User create(String name, String email) {
        User user = new User(name, email);
        user.validate();
        return user;
    }

    private void validate() {

        UserValidator validator = UserValidator.create(this);
        Notification notification = validator.validate(this);
        if (notification.hasErrors()) {
            throw UserDomainException.create(notification.getErrorsAsString());
        }
    }

    public void activate() {
        this.isActive = true;
        this.deletedAt = null;
    }

    public void deactivate() {
        this.isActive = false;
        this.deletedAt = Instant.now();
    }

    public boolean isDeleted() {
        return this.deletedAt != null;
    }

    public void update(String name, String email) {
        this.name = name;
        this.email = email;
        this.updatedAt = Instant.now();
        this.validate();
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Instant getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(Instant deletedAt) {
        this.deletedAt = deletedAt;
    }

}