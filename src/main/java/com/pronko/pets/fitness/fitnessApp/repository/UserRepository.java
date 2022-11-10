package com.pronko.pets.fitness.fitnessApp.repository;

import com.pronko.pets.fitness.fitnessApp.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);
}
