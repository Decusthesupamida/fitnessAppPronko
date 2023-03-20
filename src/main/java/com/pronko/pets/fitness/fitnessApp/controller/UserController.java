package com.pronko.pets.fitness.fitnessApp.controller;

import com.pronko.pets.fitness.fitnessApp.model.entity.User;
import com.pronko.pets.fitness.fitnessApp.service.UserService.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static com.pronko.pets.fitness.fitnessApp.controller.UserController.PATH;

@RestController
@RequestMapping(PATH)
public class UserController {

    static final String PATH = "/api/users";

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody @Valid User user) {
        return userService.createUser(user);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable("id") Long id) { return userService.deleteUser(id); }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateUser(@PathVariable("id") Long id, @RequestBody @Valid User user) {
        return userService.updateUser(id, user);
    }


}
