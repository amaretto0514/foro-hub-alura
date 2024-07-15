package com.aluracursos.foro_hub.controller;

import com.aluracursos.foro_hub.domain.service.TopicoService;
import com.aluracursos.foro_hub.domain.topico.DataSavedTopico;
import com.aluracursos.foro_hub.domain.topico.DataTopicList;
import com.aluracursos.foro_hub.domain.topico.DataTopico;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/topicos")
public class TopicoController {
    private TopicoService topicService;

    @Autowired
    public TopicoController(TopicoService topicService) {
        this.topicService = topicService;
    }

    @PostMapping
    @Transactional
    public ResponseEntity topicNew(@RequestBody @Valid DataTopico dataNewTopico){
        var responseNewTopico = topicService.addNewTopic(dataNewTopico);

        return ResponseEntity.ok(responseNewTopico);
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity topicUpdate(@PathVariable Long id, @RequestBody @Valid DataTopico dataUpdateTopico){
        var updatedTopico = topicService.updateTopic(id, dataUpdateTopico);

        return ResponseEntity.ok(new DataSavedTopico(updatedTopico));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity topicDelete(@PathVariable @Valid Long id){
        var deletedTopico = topicService.deleteTopic(id);

        return ResponseEntity.ok(deletedTopico);
    }

    @GetMapping
    public ResponseEntity<Page<DataTopicList>> topicAll(@PageableDefault(size = 10, sort = "creationDate", direction = Sort.Direction.ASC) Pageable pageable){
        return ResponseEntity.ok(topicService.listTopic(pageable));
    }

    @GetMapping("/detail/{id}")
    public ResponseEntity tipicDetail(@PathVariable @Valid Long id){
        return ResponseEntity.ok(topicService.detailTopic(id));
    }

    @PutMapping("/close/{id}")
    @Transactional
    public ResponseEntity topicClose(@PathVariable @Valid Long id){
        var closedTopic = topicService.closeTopic(id);

        return ResponseEntity.ok(closedTopic);
    }
}
