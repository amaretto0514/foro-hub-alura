package com.aluracursos.foro_hub.domain.validacion;

import com.aluracursos.foro_hub.domain.answer.DatosRegistroAnswer;
import com.aluracursos.foro_hub.domain.topico.Topico;
import com.aluracursos.foro_hub.domain.topico.TopicoRepository;
import com.aluracursos.foro_hub.infra.errors.IntegrityValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TopicClosed  implements TopicValidations{
    @Autowired
    private TopicoRepository topicRepository;

    @Override
    public void validateTopic(Object data) {
        Long idTopic = null;

        if(data instanceof Topico){
            idTopic = ((Topico) data).getId();
        }else if(data instanceof DatosRegistroAnswer){
            idTopic = ((DatosRegistroAnswer) data).topico();
        }

        if(idTopic == null){
            return;
        }

        System.out.println(idTopic);
        var topic = topicRepository.getReferenceById(idTopic);

        if(!topic.getStatus().booleanValue()){
            throw new IntegrityValidation("El topic se encuentra cerrado...");
        }
    }
}
