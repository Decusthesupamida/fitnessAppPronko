package com.pronko.pets.fitness.fitnessApp.repository;

import com.pronko.pets.fitness.fitnessApp.model.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> findByName(String roleName);
}
