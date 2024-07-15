package com.aluracursos.foro_hub.domain.validacion;

import com.aluracursos.foro_hub.domain.topico.DataTopico;
import com.aluracursos.foro_hub.domain.topico.TopicoRepository;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TopicContentExists implements TopicValidations{
    @Autowired
    private TopicoRepository topicRepository;

    @Override
    public void validateTopic(Object data) {
        if(data instanceof DataTopico){
            DataTopico dnt = (DataTopico) data;

            if(dnt.title() == null || dnt.message() == null || dnt.author() == null || dnt.curso() == null){
                return;
            }

            var topicTitle = topicRepository.findByTitle(dnt.title().replace(" ", ""));
            var topicMessage = topicRepository.findByMessage(dnt.message().replace(" ", ""));

            if(topicTitle != null){
                throw new ValidationException("El titulo ingresado ya existe...");
            }

            if(topicMessage != null){
                throw new ValidationException("El mensaje ingresado ya existe...");
            }
        }
    }
}
