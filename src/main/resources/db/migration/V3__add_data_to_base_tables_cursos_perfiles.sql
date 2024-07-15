-- Insertar en la tabla courses
INSERT INTO cursos (name, category) VALUES
('Course 01', 'Programming'),
('Course 02', 'Database'),
('Course 03', 'Frontend'),
('Course 04', 'Backend'),
('Course 05', 'Others');

-- Insertar en la tabla profiles y obtener el id insertado
WITH ins_perfil AS (
    INSERT INTO perfiles (name) VALUES ('ROLE_USER')
    RETURNING id AS perfil_id
)

-- Insertar en la tabla users y obtener el id insertado
, ins_usuario AS (
    INSERT INTO usuarios (name, email, password)
    VALUES ('First User', 'correo@ejemplo.com', '$2a$12$t3ZKVK8qDyxitGi5diSATeJASa.4UwhH933paaHwTOueAsqG9JozG')
    RETURNING id AS usuario_id
)

-- Insertar en la tabla user_profile usando los ids obtenidos anteriormente
INSERT INTO usuario_perfil (usuario_id, perfil_id)
SELECT usuario_id, perfil_id FROM ins_usuario, ins_perfil;
