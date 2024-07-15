package com.aluracursos.foro_hub.controller;

import com.aluracursos.foro_hub.domain.service.UserEntityService;
import com.aluracursos.foro_hub.domain.user.DatosRegistroUser;
import com.aluracursos.foro_hub.domain.user.DatosUserList;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/users")
public class UserEntityController {
    @Autowired
    private UserEntityService userService;

    @GetMapping
    public ResponseEntity<Page<DatosUserList>> userAll(@PageableDefault(size = 5, sort = "name") Pageable pageable){
        return ResponseEntity.ok(userService.listUsers(pageable));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity userDelete(@PathVariable @Valid Long id){
        userService.deleteUser(id);

        return ResponseEntity.ok("El usuario ha sido eliminado...");
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity userUpdate(@PathVariable @Valid Long id, @RequestBody @Valid DatosRegistroUser dataRegisterUser){
        var userResponse = userService.updateUser(id, dataRegisterUser);

        return ResponseEntity.ok(userResponse);
    }
}
