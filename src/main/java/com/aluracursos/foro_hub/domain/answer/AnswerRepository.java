package com.aluracursos.foro_hub.domain.answer;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AnswerRepository extends JpaRepository<Answer, Long> {
    @Query("""
            SELECT a
            FROM Answer a
            WHERE a.topico.id = :idTopico
            AND a.answer = :message
            """)
    Optional<Answer> findByTopicMessage(Long idTopico, String message);

}
