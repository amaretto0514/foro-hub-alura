package com.aluracursos.foro_hub.domain.topico;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DataTopico(
        @NotBlank String title,
        @NotBlank String message,
        @NotNull Long author,
        @NotNull Long curso
) {
}
