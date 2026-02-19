CREATE TABLE usuarios (
                          id UUID PRIMARY KEY,
                          nome VARCHAR(255),
                          email VARCHAR(255) NOT NULL UNIQUE,
                          senha VARCHAR(255) NOT NULL,
                          telefone VARCHAR(20),
                          data_nascimento DATE,
                          criado_em TIMESTAMP WITHOUT TIME ZONE DEFAULT CURRENT_TIMESTAMP,
                          atualizado_em TIMESTAMP WITHOUT TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE curriculos (
                            usuario_id UUID PRIMARY KEY,
                            cidade VARCHAR(100),
                            estado VARCHAR(2),
                            resumo_profissional TEXT,
                            cargo_desejado VARCHAR(255),
                            CONSTRAINT fk_usuario_curriculo FOREIGN KEY (usuario_id) REFERENCES usuarios(id) ON DELETE CASCADE
);

CREATE TABLE experiencias_profissionais (
                                            id UUID PRIMARY KEY,
                                            curriculo_id UUID NOT NULL,
                                            empresa VARCHAR(255) NOT NULL,
                                            cargo VARCHAR(255) NOT NULL,
                                            descricao TEXT,
                                            data_inicio DATE NOT NULL,
                                            data_fim DATE,
                                            atual BOOLEAN DEFAULT FALSE,
                                            CONSTRAINT fk_curriculo_experiencia FOREIGN KEY (curriculo_id) REFERENCES curriculos(usuario_id) ON DELETE CASCADE
);

CREATE TABLE formacoes_academicas (
                                      id UUID PRIMARY KEY,
                                      curriculo_id UUID NOT NULL,
                                      instituicao VARCHAR(255) NOT NULL,
                                      curso VARCHAR(255) NOT NULL,
                                      grau VARCHAR(100),
                                      data_inicio DATE,
                                      data_fim DATE,
                                      atual BOOLEAN DEFAULT FALSE,
                                      CONSTRAINT fk_curriculo_formacao FOREIGN KEY (curriculo_id) REFERENCES curriculos(usuario_id) ON DELETE CASCADE
);

CREATE TABLE curriculo_competencias (
                                        curriculo_id UUID NOT NULL,
                                        competencia VARCHAR(100) NOT NULL,
                                        CONSTRAINT fk_curriculo_competencia FOREIGN KEY (curriculo_id) REFERENCES curriculos(usuario_id) ON DELETE CASCADE
);

CREATE TABLE tokens_recuperacao_senha (
                                          id UUID PRIMARY KEY,
                                          token VARCHAR(255) NOT NULL UNIQUE,
                                          usuario_id UUID NOT NULL UNIQUE,
                                          data_expiracao TIMESTAMP WITHOUT TIME ZONE NOT NULL,
                                          criado_em TIMESTAMP WITHOUT TIME ZONE DEFAULT CURRENT_TIMESTAMP,
                                          CONSTRAINT fk_usuario_token FOREIGN KEY (usuario_id) REFERENCES usuarios(id) ON DELETE CASCADE
);