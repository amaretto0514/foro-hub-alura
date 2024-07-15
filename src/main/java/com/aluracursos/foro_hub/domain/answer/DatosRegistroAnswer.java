package com.aluracursos.foro_hub.domain.answer;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DatosRegistroAnswer(
        @NotNull Long author,
        @NotNull Long topico,
        @NotBlank String message
) {
}
