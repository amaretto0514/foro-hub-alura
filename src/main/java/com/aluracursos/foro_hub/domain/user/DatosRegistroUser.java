package com.aluracursos.foro_hub.domain.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record DatosRegistroUser(
        @NotBlank String name,
        @NotBlank @Email String email,
        @NotBlank String password
) {
}
