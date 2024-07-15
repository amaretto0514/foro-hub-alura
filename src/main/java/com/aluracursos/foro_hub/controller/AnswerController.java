package com.aluracursos.foro_hub.controller;

import com.aluracursos.foro_hub.domain.answer.DataAnswerUpdate;
import com.aluracursos.foro_hub.domain.answer.DataSavedAnswer;
import com.aluracursos.foro_hub.domain.answer.DatosRegistroAnswer;
import com.aluracursos.foro_hub.domain.service.AnswerService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/answers")
public class AnswerController {
    private AnswerService answerService;

    @Autowired
    public AnswerController(AnswerService answerService) {
        this.answerService = answerService;
    }

    @PostMapping
    @Transactional
    public ResponseEntity answerNew(@RequestBody @Valid DatosRegistroAnswer datosRegistroAnswer){
        var answer = answerService.addNewAnswer(datosRegistroAnswer);

        return ResponseEntity.ok(answer);
    }

    @PutMapping("/solution/{id}")
    @Transactional
    public ResponseEntity answerSolution(@PathVariable @Valid Long id){
        var answer = answerService.solutionAnswer(id);

        return ResponseEntity.ok(answer);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity answerDelete(@PathVariable @Valid Long id){
        var answer = answerService.deleteAnswer(id);

        return ResponseEntity.ok(answer);
    }

    @PutMapping
    @Transactional
    public ResponseEntity answerUpdate(@RequestBody @Valid DataAnswerUpdate dataAnswerUpdate){
        var answer = answerService.updateAnswer(dataAnswerUpdate);

        return ResponseEntity.ok(new DataSavedAnswer(answer));
    }
}
