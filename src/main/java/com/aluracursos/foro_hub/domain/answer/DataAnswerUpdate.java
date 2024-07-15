package com.aluracursos.foro_hub.domain.answer;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DataAnswerUpdate( @NotNull Long idAnswer,
                                @NotBlank String newMessage) {
}
