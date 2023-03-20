package com.pronko.pets.fitness.fitnessApp.mapper;

import com.pronko.pets.fitness.fitnessApp.model.entity.User;
import com.pronko.pets.fitness.fitnessApp.model.request.AuthRequest;
import com.pronko.pets.fitness.fitnessApp.model.request.SignUpRequest;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring")
public interface UserMapper {

    User signUpRequestToEntity(SignUpRequest signUpRequest);

    AuthRequest entityToAuthRequest(User user);

}
