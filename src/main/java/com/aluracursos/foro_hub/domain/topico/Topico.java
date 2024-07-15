package com.aluracursos.foro_hub.domain.topico;

import com.aluracursos.foro_hub.domain.curso.Curso;
import com.aluracursos.foro_hub.domain.user.UserEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Table(name = "topicos")
@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Topico {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String title;

    @Column(unique = true)
    private String message;
    private LocalDateTime creationDate;
    private Boolean status;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private UserEntity author;

    @ManyToOne
    @JoinColumn(name = "curso_id", nullable = false)
    private Curso curso;
    private Integer answers;
    public Topico(String title, String message, LocalDateTime creationDate, UserEntity userEntity, Curso course){
        this.title = title;
        this.message = message;
        this.creationDate = creationDate;
        this.author = userEntity;
        this.curso = curso;
        this.answers = 0;
        this.status = true;
    }

    public void updateTopico(DataTopico dataTopic, Curso course){
        if(dataTopic.title() != null){
            this.title = dataTopic.title();
        }

        if(dataTopic.message() != null){
            this.message = dataTopic.message();
        }

        if(course != null){
            this.curso = curso;
        }
    }

    //cerrara un topic y no se podra responder
    public void closeTopic(){
        this.status = false;
    }
}
