package com.businessbrasil.api.api_cadastro_usuarios.entity;

import com.businessbrasil.api.api_cadastro_usuarios.domain.DomainException;
import com.businessbrasil.api.api_cadastro_usuarios.domain.Notification;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;
import java.util.UUID;
import java.util.regex.Pattern;

@Entity
@Table(name = "users", indexes = {
        @Index(name = "idx_users_email", columnList = "email"),
        @Index(name = "idx_users_active", columnList = "is_active"),
        @Index(name = "idx_users_created_at", columnList = "created_at"),
        @Index(name = "idx_users_deleted_at", columnList = "deleted_at")
})
public class User {

    private static final Pattern EMAIL_PATTERN = Pattern.compile(
            "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$");

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "name", nullable = false, length = 255)
    private String name;

    @Column(name = "email", nullable = false, unique = true, length = 255)
    private String email;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive = true;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private Instant updatedAt;

    @Column(name = "deleted_at")
    private Instant deletedAt;

    private User() {
        this.isActive = true;
    }

    private User(String name, String email) {
        this();
        this.name = name;
        this.email = email;
    }

    public static User create(String name, String email) {
        Notification notification = validate(name, email);
        if (notification.hasErrors()) {
            throw DomainException.create(notification);
        }
        return new User(name, email);
    }

    public static User createWithId(UUID id, String name, String email) {
        Notification notification = validate(name, email);
        if (notification.hasErrors()) {
            throw DomainException.create(notification);
        }
        User user = new User(name, email);
        user.id = id;
        return user;
    }

    public static Notification validate(String name, String email) {
        Notification notification = Notification.create();

        if (name == null || name.trim().isEmpty()) {
            notification.addError("name", "Nome é obrigatório");
        } else if (name.trim().length() < 2) {
            notification.addError("name", "Nome deve ter pelo menos 2 caracteres");
        } else if (name.trim().length() > 255) {
            notification.addError("name", "Nome deve ter no máximo 255 caracteres");
        }

        if (email == null || email.trim().isEmpty()) {
            notification.addError("email", "Email é obrigatório");
        } else if (!EMAIL_PATTERN.matcher(email.trim()).matches()) {
            notification.addError("email", "Email deve ter um formato válido");
        } else if (email.trim().length() > 255) {
            notification.addError("email", "Email deve ter no máximo 255 caracteres");
        }

        return notification;
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
        Notification notification = validate(name, email);
        if (notification.hasErrors()) {
            throw DomainException.create(notification);
        }
        this.name = name.trim();
        this.email = email.trim();
    }

    public Notification validateCurrentState() {
        return validate(this.name, this.email);
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

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", isActive=" + isActive +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", deletedAt=" + deletedAt +
                '}';
    }
}