package com.aluracursos.foro_hub.domain.answer;

import java.time.LocalDateTime;

public record DataSavedAnswer(Long idAnswer,
                              String message,
                              String topicoTitle,
                              LocalDateTime creationDate
) {
    public DataSavedAnswer(Answer answer){
        this(
                answer.getId(),
                answer.getAnswer(),
                answer.getTopico().getTitle(),
                answer.getCreationDate()
        );
    }
}
