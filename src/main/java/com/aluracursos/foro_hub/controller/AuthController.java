package com.aluracursos.foro_hub.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;


import com.aluracursos.foro_hub.domain.service.UserEntityService;
import com.aluracursos.foro_hub.domain.user.DatosAutenticacionUser;
import com.aluracursos.foro_hub.domain.user.DatosRegistroUser;
import com.aluracursos.foro_hub.domain.user.UserEntity;
import com.aluracursos.foro_hub.infra.security.DataJwtToken;
import com.aluracursos.foro_hub.infra.security.TokenService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
;

@CrossOrigin
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private AuthenticationManager authManager;
    private TokenService tokenService;
    private UserEntityService userEntityService;

    @Autowired
    public AuthController(AuthenticationManager authManager, TokenService tokenService, UserEntityService userEntityService) {
        this.authManager = authManager;
        this.tokenService = tokenService;
        this.userEntityService = userEntityService;
    }

    @PostMapping("/login")
    public ResponseEntity authUser(@RequestBody @Valid DatosAutenticacionUser dataAuthUser){
        Authentication authToken = new UsernamePasswordAuthenticationToken(dataAuthUser.email(), dataAuthUser.password());
        var userAuthenticated = authManager.authenticate(authToken);
        var jwtToken = tokenService.generateToken((UserEntity) userAuthenticated.getPrincipal());

        return ResponseEntity.ok(new DataJwtToken(jwtToken));
    }

    @PostMapping("/register")
    @Transactional
    public ResponseEntity registerUser(@RequestBody @Valid DatosRegistroUser dataRegisterUser, UriComponentsBuilder uriBuilder){
        var response = userEntityService.registerUser(dataRegisterUser);

        if(Boolean.FALSE.equals(response)){
            return ResponseEntity.badRequest().body("Email ya esta en uso...");
        }

        return ResponseEntity.ok("Usuario ha sido creado...");
    }
}
