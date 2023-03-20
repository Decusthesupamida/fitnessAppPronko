package com.pronko.pets.fitness.fitnessApp.model.request;

import com.pronko.pets.fitness.fitnessApp.model.enums.GenderEnum;
import lombok.Builder;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
@Builder
public class SignUpRequest {

    @Email
    @Length(min = 10, max = 50)
    private String username;

    @Length(min = 8, max = 50)
    private String password;

    @Length(min = 8, max = 50)
    private String passwordConfirm;

    @Length(min = 2, max = 20)
    private String firstname;

    @Length(min = 2, max = 20)
    private String lastname;

    @NotNull
    private GenderEnum gender;

    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private LocalDate birthdate;
}
