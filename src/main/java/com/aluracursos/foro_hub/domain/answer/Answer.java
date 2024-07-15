package com.aluracursos.foro_hub.domain.answer;

import com.aluracursos.foro_hub.domain.topico.Topico;
import com.aluracursos.foro_hub.domain.user.UserEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Table(name = "answers")
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Answer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "message")
    private String answer;

    @ManyToOne
    @JoinColumn(name = "topico_id", nullable = false)
    private Topico topico;
    private LocalDateTime creationDate;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private UserEntity author;
    private Boolean solution;

    //Constructor para crear una respuesta
    public Answer(String answer, LocalDateTime creationDate, Topico topico, UserEntity author){
        this.answer = answer;
        this.creationDate = creationDate;
        this.topico = topico;
        this.author = author;
        this.solution = false;
    }

    public void updateAnswer(DataAnswerUpdate dataAnswerUpdate){
        if(dataAnswerUpdate.newMessage() != null){
            this.answer = dataAnswerUpdate.newMessage();
        }
    }

    //metodo para marcar la respuesta como solucionada
    public void markSolution(){
        this.solution = true;
    }
}


