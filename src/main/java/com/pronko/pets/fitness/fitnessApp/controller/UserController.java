package com.pronko.pets.fitness.fitnessApp.controller;

import com.pronko.pets.fitness.fitnessApp.entity.User;
import com.pronko.pets.fitness.fitnessApp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import java.net.URI;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping
    @RolesAllowed("ROLE_ADMIN")
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        User savedUser = userRepository.save(user);
        URI userUri = URI.create("/api/users" + savedUser.getId());

        return ResponseEntity.created(userUri).body(savedUser);
    }
}
