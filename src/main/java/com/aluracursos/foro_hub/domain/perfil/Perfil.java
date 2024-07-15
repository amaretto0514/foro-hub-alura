package com.aluracursos.foro_hub.domain.perfil;

import com.aluracursos.foro_hub.domain.user.UserEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Set;

@Table(name = "perfiles")
@Entity(name = "Perfil")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Perfil {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @JsonIgnore //previene problema inifinite recursion en el json
    @ManyToMany(mappedBy = "perfiles")
    private Set<UserEntity> usuarios;
}
