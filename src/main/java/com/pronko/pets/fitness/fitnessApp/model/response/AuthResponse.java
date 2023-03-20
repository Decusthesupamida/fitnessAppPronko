package com.pronko.pets.fitness.fitnessApp.model.response;

import lombok.*;

@Data
@Builder
public class AuthResponse {
    private String username;
    private String token;
}
