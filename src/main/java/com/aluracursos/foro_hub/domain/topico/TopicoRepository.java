package com.aluracursos.foro_hub.domain.topico;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface TopicoRepository extends JpaRepository<Topico,Long> {
    @Query("""
                SELECT t.title
                FROM Topico t
                WHERE REPLACE(t.title, " ", "") = :title
                """)
    String findByTitle(String title);

    @Query("""
                SELECT t.message
                FROM Topico t
                WHERE REPLACE(t.message, " ", "") = :message
                """)
    String findByMessage(String message);

    @Query("""
                SELECT t
                FROM Topico t
                WHERE t.id = :idTopico
                AND t.author.id = :idAuthor        
                """)
    Optional<Topico> findByIdAndAuthorId(Long idTopico, Long idAuthor);

    @Query("""
                SELECT t
                FROM Topico t
                WHERE t.id = :idTopico
                AND t.status = true
                """)
    Optional<Topico> findByIdAndStatus(Long idTopico);
}

