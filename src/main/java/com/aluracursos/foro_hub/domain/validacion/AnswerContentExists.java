package com.aluracursos.foro_hub.domain.validacion;

import com.aluracursos.foro_hub.domain.answer.AnswerRepository;
import com.aluracursos.foro_hub.domain.answer.DataAnswerUpdate;
import com.aluracursos.foro_hub.domain.answer.DatosRegistroAnswer;
import com.aluracursos.foro_hub.infra.errors.IntegrityValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AnswerContentExists implements AnswerValidations{
    @Autowired
    private AnswerRepository answerRepository;

    @Override
    public void validateAnswer(Object data) {
        Long idTopic = null;
        String message = null;

        if(data instanceof DatosRegistroAnswer){
            var dataAnswer = (DatosRegistroAnswer) data;
            idTopic = dataAnswer.topico();
            message = dataAnswer.message();
        }else if(data instanceof DataAnswerUpdate){
            var dataAnswerUpdate = (DataAnswerUpdate) data;
            var idAnswer = dataAnswerUpdate.idAnswer();
            var answerData = answerRepository.getReferenceById(idAnswer);
            idTopic = answerData.getTopico().getId();
            message = dataAnswerUpdate.newMessage();
        }

        //validar que el mensaje no exista para el topico
        var messageExist = answerRepository.findByTopicMessage(idTopic, message);

        if(messageExist.isPresent()){
            throw new IntegrityValidation("La respuesta ya existe para el topico...");
        }
    }
}
