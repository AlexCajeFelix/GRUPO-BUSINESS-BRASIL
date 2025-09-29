package com.businessbrasil.api.api_cadastro_usuarios.repository;

import com.businessbrasil.api.api_cadastro_usuarios.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Repository para operações de banco de dados da entidade User
 * Desafio Técnico Grupo Business Brasil
 * 
 * @author Alex Caje Felix
 * @version 1.0.0
 */
@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

    /**
     * Busca usuário por email
     * @param email email do usuário
     * @return Optional<User>
     */
    Optional<User> findByEmail(String email);

    /**
     * Verifica se existe usuário com email
     * @param email email do usuário
     * @return boolean
     */
    boolean existsByEmail(String email);

    /**
     * Busca usuários ativos
     * @param pageable configuração de paginação
     * @return Page<User>
     */
    @Query("SELECT u FROM User u WHERE u.isActive = true AND u.deletedAt IS NULL")
    Page<User> findActiveUsers(Pageable pageable);

    /**
     * Busca usuários ativos sem paginação
     * @return List<User>
     */
    @Query("SELECT u FROM User u WHERE u.isActive = true AND u.deletedAt IS NULL")
    List<User> findAllActiveUsers();

    /**
     * Busca usuário por ID apenas se estiver ativo
     * @param id ID do usuário
     * @return Optional<User>
     */
    @Query("SELECT u FROM User u WHERE u.id = :id AND u.isActive = true AND u.deletedAt IS NULL")
    Optional<User> findActiveById(@Param("id") UUID id);

    /**
     * Busca usuários por nome (busca parcial)
     * @param name nome ou parte do nome
     * @param pageable configuração de paginação
     * @return Page<User>
     */
    @Query("SELECT u FROM User u WHERE LOWER(u.name) LIKE LOWER(CONCAT('%', :name, '%')) AND u.isActive = true AND u.deletedAt IS NULL")
    Page<User> findByNameContainingIgnoreCase(@Param("name") String name, Pageable pageable);

    /**
     * Conta usuários ativos
     * @return long
     */
    @Query("SELECT COUNT(u) FROM User u WHERE u.isActive = true AND u.deletedAt IS NULL")
    long countActiveUsers();

    /**
     * Soft delete - marca usuário como deletado
     * @param id ID do usuário
     */
    @Query("UPDATE User u SET u.isActive = false, u.deletedAt = CURRENT_TIMESTAMP WHERE u.id = :id")
    void softDeleteById(@Param("id") UUID id);
}
