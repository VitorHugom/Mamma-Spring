CREATE TABLE fornecedores (
    id SERIAL PRIMARY KEY,
    razao_social VARCHAR(255) NOT NULL,
    nome_fantasia VARCHAR(255),
    cpf VARCHAR(14),
    cnpj VARCHAR(18),
    tipo_pessoa VARCHAR(20) CHECK (tipo_pessoa IN ('fisica', 'juridica')),
    cep VARCHAR(10),
    endereco VARCHAR(255),
    complemento VARCHAR(255),
    numero VARCHAR(10),
    bairro VARCHAR(100),
    cidade_id INTEGER REFERENCES cidades(id),
    celular VARCHAR(15),
    telefone VARCHAR(15),
    email VARCHAR(255) UNIQUE,
    estado_inscricao_estadual BOOLEAN DEFAULT FALSE,
    inscricao_estadual VARCHAR(30),
    observacao TEXT,
    status BOOLEAN DEFAULT TRUE,
    data_cadastro TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);