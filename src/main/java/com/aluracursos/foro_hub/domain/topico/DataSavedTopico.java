package com.aluracursos.foro_hub.domain.topico;

import java.time.LocalDateTime;

public record DataSavedTopico(Long id,
                              String title,
                              String message,
                              LocalDateTime createdDate
) {
    public DataSavedTopico(Topico topico){
        this(
                topico.getId(),
                topico.getTitle(),
                topico.getMessage(),
                topico.getCreationDate()
        );
    }
}
