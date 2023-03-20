package com.pronko.pets.fitness.fitnessApp.service.UserService;

import com.pronko.pets.fitness.fitnessApp.model.entity.Role;
import com.pronko.pets.fitness.fitnessApp.model.entity.User;
import com.pronko.pets.fitness.fitnessApp.repository.UserRepository;
import com.pronko.pets.fitness.fitnessApp.utils.exception.CustomNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.util.HashSet;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;


    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public ResponseEntity<User> createUser(User user) {
        User savedUser = userRepository.save(user);
        URI userUri = URI.create("/api/users" + savedUser.getId());

        return ResponseEntity.created(userUri).body(savedUser);
    }

    @Override
    public ResponseEntity<User> updateUser(Long userId, User user) {
        User findedUser = userRepository.findById(userId).orElseThrow(CustomNotFoundException::new);
        findedUser.setUsername(user.getUsername());
        findedUser.setFirstname(user.getFirstname());
        findedUser.setLastname(user.getLastname());
        User savedUser = userRepository.save(findedUser);
        return ResponseEntity.status(HttpStatus.OK).body(savedUser);
    }

    @Override
    public ResponseEntity<User> deleteUser(Long userId) {
        User findedUser = userRepository.findById(userId).orElseThrow(CustomNotFoundException::new);
        userRepository.deleteById(findedUser.getId());
        return ResponseEntity.status(HttpStatus.OK).body(findedUser);
    }

    @Override
    public ResponseEntity<User> addRoles(Long userId, Role role) {
        User findedUser = userRepository.findById(userId).orElseThrow(CustomNotFoundException::new);
        findedUser.addRole(role);
        userRepository.save(findedUser);
        return ResponseEntity.status(HttpStatus.OK).body(findedUser);
    }

    @Override
    public ResponseEntity<User> setRoles(Long userId, List<Role> roles) {
        User findedUser = userRepository.findById(userId).orElseThrow(CustomNotFoundException::new);
        findedUser.setRoles(new HashSet<>(roles));
        userRepository.save(findedUser);
        return ResponseEntity.status(HttpStatus.OK).body(findedUser);
    }


}
