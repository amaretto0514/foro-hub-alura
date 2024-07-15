package com.aluracursos.foro_hub.domain.user;

import jakarta.validation.constraints.Email;

public record DatosAutenticacionUser(
        @Email String email,
        String password
) {
}
