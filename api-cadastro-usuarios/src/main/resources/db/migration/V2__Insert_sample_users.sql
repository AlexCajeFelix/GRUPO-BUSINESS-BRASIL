-- Migration: V2__Insert_sample_users.sql
-- Author: Alex Caje Felix
-- Description: Insere dados de exemplo para testes
-- Date: 2025-09-29

-- Inserir usuários de exemplo para testes
INSERT INTO users (id, name, email, is_active, created_at, updated_at) VALUES
    ('550e8400-e29b-41d4-a716-446655440001', 'João Silva', 'joao.silva@email.com', TRUE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('550e8400-e29b-41d4-a716-446655440002', 'Maria Santos', 'maria.santos@email.com', TRUE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('550e8400-e29b-41d4-a716-446655440003', 'Pedro Oliveira', 'pedro.oliveira@email.com', TRUE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('550e8400-e29b-41d4-a716-446655440004', 'Ana Costa', 'ana.costa@email.com', TRUE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('550e8400-e29b-41d4-a716-446655440005', 'Carlos Ferreira', 'carlos.ferreira@email.com', FALSE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Comentário sobre os dados inseridos
COMMENT ON TABLE users IS 'Dados de exemplo inseridos para testes da API';
