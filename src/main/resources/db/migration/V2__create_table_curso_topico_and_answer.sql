CREATE TABLE cursos (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    category VARCHAR(100) NOT NULL
);

CREATE TABLE topicos (
    id BIGSERIAL PRIMARY KEY,
    title VARCHAR(100) NOT NULL UNIQUE,
    message VARCHAR(250) NOT NULL UNIQUE,
    creation_date TIMESTAMP NOT NULL,
    status SMALLINT NOT NULL DEFAULT 0,
    answers BIGINT NOT NULL DEFAULT 0,
    usuario_id BIGINT NOT NULL,
    curso_id BIGINT NOT NULL,

    CONSTRAINT fk_topicos_cursos_id FOREIGN KEY (curso_id) REFERENCES cursos (id),
    CONSTRAINT fk_topicos_usuarios_id FOREIGN KEY (usuario_id) REFERENCES usuarios (id)
);

CREATE TABLE answers (
    id BIGSERIAL PRIMARY KEY,
    message VARCHAR(250) NOT NULL,
    creation_date TIMESTAMP NOT NULL,
    solution SMALLINT NOT NULL DEFAULT 0,
    topico_id BIGINT NOT NULL,
    usuario_id BIGINT NOT NULL,

    CONSTRAINT fk_answers_topicos_id FOREIGN KEY (topico_id) REFERENCES topicos (id),
    CONSTRAINT fk_answers_usuarios_id FOREIGN KEY (usuario_id) REFERENCES usuarios (id)
);
