package com.pronko.pets.fitness.fitnessApp.controller;

import com.pronko.pets.fitness.fitnessApp.model.request.AuthRequest;
import com.pronko.pets.fitness.fitnessApp.model.request.SignUpRequest;
import com.pronko.pets.fitness.fitnessApp.service.AuthService.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static com.pronko.pets.fitness.fitnessApp.controller.AuthController.PATH;

@RestController
@RequestMapping(PATH)
@RequiredArgsConstructor
@Validated
public class AuthController {


    static final String PATH = "/auth";
    static final String LOGIN = "/login";
    static final String SIGNUP = "/signup";

    private final AuthService authService;


    @PostMapping(LOGIN)
    public ResponseEntity<?> login(@RequestBody @Valid AuthRequest authRequest) {
        return authService.login(authRequest);
    }

    @PostMapping(SIGNUP)
    public ResponseEntity<?> signup(@RequestBody @Valid SignUpRequest signUpRequest) {
        return authService.signUp(signUpRequest);
    }
}
