package com.aluracursos.foro_hub.domain.service;

import com.aluracursos.foro_hub.domain.curso.Curso;
import com.aluracursos.foro_hub.domain.curso.CursoRepository;
import com.aluracursos.foro_hub.domain.topico.*;
import com.aluracursos.foro_hub.domain.user.UserEntity;
import com.aluracursos.foro_hub.domain.user.UserRepository;
import com.aluracursos.foro_hub.domain.validacion.GeneralValidations;
import com.aluracursos.foro_hub.domain.validacion.TopicValidations;
import com.aluracursos.foro_hub.infra.errors.IntegrityValidation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TopicoService {
    private TopicoRepository topicRepository;
    private UserRepository userRepository;
    private CursoRepository courseRepository;
    private List<TopicValidations> topicValidations;
    private List<GeneralValidations> generalValidations;

    @Autowired
    public TopicoService(TopicoRepository topicRepository, UserRepository userRepository,
                        CursoRepository courseRepository, List<TopicValidations> topicValidations, List<GeneralValidations> generalValidations) {
        this.topicRepository = topicRepository;
        this.userRepository = userRepository;
        this.courseRepository = courseRepository;
        this.topicValidations = topicValidations;
        this.generalValidations = generalValidations;
    }

    public DataSavedTopico addNewTopic(DataTopico dataNewTopic) {
        //que el curso y el usuario exista
        var methodCurso = getSelectedCurso(dataNewTopic);
        var methodUser = getSelectedUser(dataNewTopic);

        //que los titulos y mensajes no sean iguales
        topicValidations.forEach(tv -> tv.validateTopic(dataNewTopic));

        var currentDate = LocalDateTime.now();
        var newTopic = new Topico(
                dataNewTopic.title(),
                dataNewTopic.message(),
                currentDate,
                methodUser,
                methodCurso
        );

        topicRepository.save(newTopic);

        return new DataSavedTopico(newTopic);
    }



    public Topico updateTopic(Long id, @Valid DataTopico dataUpdateTopic) {
        //que no venga nada null -> ya lo valida en el record DataTopic
        //exista el id
        if(!topicRepository.existsById(id)){
            throw new IntegrityValidation("El topic a actualizar no ha sido encontrado...");
        }

        //valida que el autor sea el mismo del topic a eliminar via custom validation
        //generalValidations.forEach(gv -> gv.validateGeneral(topicToDelete.get()));

        //que el id del autor sea el mismo del topic via sql
        if(!topicRepository.findByIdAndAuthorId(id, dataUpdateTopic.author()).isPresent()){
            throw new IntegrityValidation("El autor no corresponde al topic a actualizar...");
        }

        //que los titulos y mensajes no sean iguales
        topicValidations.forEach(tv -> tv.validateTopic(dataUpdateTopic));

        //que el curso y el usuario exista
        var methodCourse = getSelectedCurso(dataUpdateTopic);
        var methodUser = getSelectedUser(dataUpdateTopic);

        //actualizar
        Topico updateTopic = topicRepository.getReferenceById(id);
        updateTopic.updateTopico(dataUpdateTopic, methodCourse);

        //retorna datos actualizados, excepto fecha de creacion
        return updateTopic;
    }

    public String deleteTopic(Long id){
        var topicToDelete = topicRepository.findById(id);

        //exista el id
        if(!topicToDelete.isPresent()){
            throw new IntegrityValidation("El topic a eliminar no ha sido encontrado...");
        }

        //valida que el autor sea el mismo del topic a eliminar via custom validation
        generalValidations.forEach(gv -> gv.validateGeneral(topicToDelete.get()));

        //valida que el topic no este cerrado
        //topicValidations.forEach(tv -> tv.validateTopic(topicToDelete.get()));

        //eliminar topic
        topicRepository.deleteById(id);

        //mensaje de que ha sido eliminado
        return "El topic ha sido eliminado...";
    }

    public Page<DataTopicList> listTopic(Pageable pageable){
        return topicRepository.findAll(pageable).map(DataTopicList::new);
    }

    public DataTopicList detailTopic(@Valid Long id) {
        //exista el id
        if(!topicRepository.existsById(id)){
            throw new IntegrityValidation("El topic a consultar no ha sido encontrado...");
        }

        return new DataTopicList(topicRepository.getReferenceById(id));
    }

    public String closeTopic(@Valid Long id) {
        if(id == null){
            throw new IntegrityValidation("Ha ocurrido un problema con el paramatro enviado...");
        }

        if(!topicRepository.existsById(id)){
            throw new IntegrityValidation("El topic a cerrar no ha sido encontrado...");
        }

        var topicData = topicRepository.getReferenceById(id);

        generalValidations.forEach(gv -> gv.validateGeneral(topicData));
        topicValidations.forEach(tv -> tv.validateTopic(topicData));

        topicData.closeTopic();

        return "Se ha cerradp el topic";
    }

    private Curso getSelectedCurso(DataTopico data){
        if(!courseRepository.existsById(data.curso())){
            throw new IntegrityValidation("El curso ingresado es invalido...");
        }

        return courseRepository.getReferenceById(data.curso());
    }

    private UserEntity getSelectedUser(DataTopico data){
        if(!userRepository.existsById(data.author())){
            throw new IntegrityValidation("El usuario ingresado es invalido...");
        }

        return userRepository.getReferenceById(data.author());
    }
}
