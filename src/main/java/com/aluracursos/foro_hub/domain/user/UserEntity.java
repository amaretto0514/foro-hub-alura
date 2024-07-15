package com.aluracursos.foro_hub.domain.user;

import com.aluracursos.foro_hub.domain.answer.Answer;
import com.aluracursos.foro_hub.domain.perfil.Perfil;
import com.aluracursos.foro_hub.domain.topico.Topico;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Table(name = "usuarios")
@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class UserEntity implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String email;
    private String password;
    private Boolean active;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(
            name = "usuario_perfil",
            joinColumns = @JoinColumn(name = "usuario_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "perfil_id", referencedColumnName = "id")
    )
    private Set<Perfil> perfiles;

    @OneToMany(mappedBy = "author")
    private List<Answer> answers;

    @OneToMany(mappedBy = "author")
    private List<Topico> topicos;

    public UserEntity(String name, String email, String password, Set<Perfil> perfil){
        this.name = name;
        this.email = email;
        this.password = password;
        this.active = true;
        this.perfiles = perfil;
    }

    public void deactivateUser(){
        this.active = false;
    }

    public void updateUser(String nameIn, String emailIn, String passwordIn) {
        if(nameIn != null){
            this.name = nameIn;
        }

        if(emailIn != null){
            this.email = emailIn;
        }

        if(passwordIn != null){
            this.password = passwordIn;
        }
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();

        this.perfiles.forEach(p -> {
            authorities.add(new SimpleGrantedAuthority(p.getName()));
        });

        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return name;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return this.active;
    }
}
