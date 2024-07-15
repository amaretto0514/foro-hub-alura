package com.aluracursos.foro_hub.domain.validacion;

import com.aluracursos.foro_hub.domain.answer.Answer;
import com.aluracursos.foro_hub.infra.errors.IntegrityValidation;
import org.springframework.stereotype.Component;

@Component
public class AnswerMarkedSolution implements AnswerValidations{

    @Override
    public void validateAnswer(Object data) {
        if(data instanceof Answer){
            Answer answer = (Answer) data;

            if(answer.getSolution().booleanValue()){
                throw new IntegrityValidation("La respuesta ya ha sido marcada como solucion...");
            }
        }
    }
}
