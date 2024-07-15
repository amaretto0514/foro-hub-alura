package com.aluracursos.foro_hub.domain.topico;

import java.time.LocalDateTime;

public record DataTopicList(String title,
                            String message,
                            LocalDateTime creationDate,
                            String status,
                            String author,
                            String curso,
                            Integer answers
) {
    public DataTopicList(Topico topico){
        this(
                topico.getTitle(),
                topico.getMessage(),
                topico.getCreationDate(),
                topico.getStatus().booleanValue()?"Active":"Closed",
                topico.getAuthor().getName(),
                topico.getCurso().getName(),
                topico.getAnswers()
        );
    }
}
