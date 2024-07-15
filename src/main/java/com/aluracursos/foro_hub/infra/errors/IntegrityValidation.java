package com.aluracursos.foro_hub.infra.errors;

public class IntegrityValidation extends RuntimeException{
    public IntegrityValidation(String msg){
        super(msg);
    }
}
