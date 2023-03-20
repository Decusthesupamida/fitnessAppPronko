package com.pronko.pets.fitness.fitnessApp.service.UserService;

import com.pronko.pets.fitness.fitnessApp.model.entity.Role;
import com.pronko.pets.fitness.fitnessApp.model.entity.User;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface UserService {
    public List<User> getAllUsers();
    public ResponseEntity<User> createUser(User user);
    public ResponseEntity<User> updateUser(Long userId, User user);
    public ResponseEntity<User> deleteUser(Long userId);
    public ResponseEntity<User> addRoles(Long userId, Role role);
    public ResponseEntity<User> setRoles(Long userId, List<Role> roles);

}
