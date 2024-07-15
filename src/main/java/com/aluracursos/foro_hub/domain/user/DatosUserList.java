package com.aluracursos.foro_hub.domain.user;

import com.aluracursos.foro_hub.domain.perfil.Perfil;

import java.util.List;

public record DatosUserList(String name,
                            String status,
                            List<String> perfiles,
                            int qtyCreatedTopics,
                            int qtyAnsweredTopics
) {
    public DatosUserList(UserEntity userEntity){
        this(
                userEntity.getName(),
                Boolean.TRUE.equals(userEntity.getActive())?"Active":"Inactive",
                userEntity.getPerfiles().stream().map(Perfil::getName).toList(),
                userEntity.getTopicos().size(),
                userEntity.getAnswers().size()
        );
    }
}
