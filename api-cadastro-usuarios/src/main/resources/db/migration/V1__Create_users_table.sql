
CREATE TABLE users (
    id UUID PRIMARY KEY DEFAULT RANDOM_UUID(),
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    is_active BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    deleted_at TIMESTAMP NULL
);

-- Criar índices para otimização de consultas
CREATE INDEX idx_users_email ON users(email);
CREATE INDEX idx_users_active ON users(is_active);
CREATE INDEX idx_users_created_at ON users(created_at);
CREATE INDEX idx_users_deleted_at ON users(deleted_at);


COMMENT ON TABLE users IS 'Tabela de usuários do sistema - Desafio Técnico Grupo Business Brasil';
COMMENT ON COLUMN users.id IS 'Identificador único do usuário (UUID)';
COMMENT ON COLUMN users.name IS 'Nome completo do usuário';
COMMENT ON COLUMN users.email IS 'Email único do usuário (validado)';
COMMENT ON COLUMN users.is_active IS 'Status ativo/inativo do usuário (soft delete)';
COMMENT ON COLUMN users.created_at IS 'Data e hora de criação do registro';
COMMENT ON COLUMN users.updated_at IS 'Data e hora da última atualização';
COMMENT ON COLUMN users.deleted_at IS 'Data e hora da exclusão lógica (soft delete)';
