package com.pronko.pets.fitness.fitnessApp.model.request;

import lombok.Builder;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;

@Data
@Builder
public class AuthRequest {
    @Email @Length(min = 4, max = 50)
    private String username;
    @Length(min = 8, max = 50)
    private String password;
}
