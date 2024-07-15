package com.aluracursos.foro_hub.domain.service;

import com.aluracursos.foro_hub.domain.perfil.PerfilRepository;
import com.aluracursos.foro_hub.domain.user.DatosRegistroUser;
import com.aluracursos.foro_hub.domain.user.DatosUserList;
import com.aluracursos.foro_hub.domain.user.UserEntity;
import com.aluracursos.foro_hub.domain.user.UserRepository;
import com.aluracursos.foro_hub.infra.errors.IntegrityValidation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class UserEntityService {
    private UserRepository userRepository;
    private PerfilRepository profileRepository;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserEntityService(UserRepository userRepository, PerfilRepository profileRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.profileRepository = profileRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Boolean registerUser(DatosRegistroUser dataRegisterUser){
        if(dataRegisterUser.email() != null && userRepository.existsByEmail(dataRegisterUser.email()).booleanValue()){
            return false;
        }

        var dataProfile = profileRepository.findByName("ROLE_USER");
        var newUser = new UserEntity(
                dataRegisterUser.name(),
                dataRegisterUser.email(),
                passwordEncoder.encode(dataRegisterUser.password()),
                Set.of(dataProfile)
        );

        userRepository.save(newUser);

        return true;
    }

    public Page<DatosUserList> listUsers(Pageable pageable){
        return userRepository.findAll(pageable).map(DatosUserList::new);
    }

    public void deleteUser(@Valid Long id) {
        if(userIsEnabledAndExist(id)){
            throw new IntegrityValidation("El usuario a eliminar no existe...");
        }

        var user = userRepository.getReferenceById(id);
        user.deactivateUser();
    }

    public UserEntity updateUser(@Valid Long id, @Valid DatosRegistroUser dataRegisterUser) {
        if(userIsEnabledAndExist(id)){
            throw new IntegrityValidation("El usuario a actualizar no existe...");
        }

        //validar que el correo no exista
        if(userRepository.existsByEmail(dataRegisterUser.email()).booleanValue()){
            throw new IntegrityValidation("El correo ya existe...");
        }

        UserEntity userEntity = userRepository.getReferenceById(id);
        userEntity.updateUser(
                dataRegisterUser.name(),
                dataRegisterUser.email(),
                passwordEncoder.encode(dataRegisterUser.password())
        );

        return userEntity;
    }

    private boolean userIsEnabledAndExist(Long id){
        var userExist = userRepository.findById(id);

        //validar que exista el id o este habilitado
        return (!userExist.isPresent() || !userExist.get().isEnabled());
    }
}
