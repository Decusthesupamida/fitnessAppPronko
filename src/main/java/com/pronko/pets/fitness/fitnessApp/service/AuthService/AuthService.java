package com.pronko.pets.fitness.fitnessApp.service.AuthService;

import com.pronko.pets.fitness.fitnessApp.model.request.AuthRequest;
import com.pronko.pets.fitness.fitnessApp.model.request.SignUpRequest;
import org.springframework.http.ResponseEntity;

public interface AuthService {
    ResponseEntity<?> login (AuthRequest authRequest);

    ResponseEntity<?> signUp (SignUpRequest signUpRequest);
}
