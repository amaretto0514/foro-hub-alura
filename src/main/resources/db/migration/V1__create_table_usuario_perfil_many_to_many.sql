CREATE TABLE usuarios (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL,
    password VARCHAR(300) NOT NULL
);

CREATE TABLE perfiles(
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL
);

CREATE TABLE usuario_perfil (
    usuario_id BIGINT NOT NULL,
    perfil_id BIGINT NOT NULL,

    CONSTRAINT fk_usuario_id FOREIGN KEY (usuario_id) REFERENCES usuarios (id),
    CONSTRAINT fk_perfil_id FOREIGN KEY (perfil_id) REFERENCES perfiles (id)
);
