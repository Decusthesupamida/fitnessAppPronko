package com.pronko.pets.fitness.fitnessApp.service.AuthService;

import com.pronko.pets.fitness.fitnessApp.exceptions.ApiException;
import com.pronko.pets.fitness.fitnessApp.mapper.UserMapper;
import com.pronko.pets.fitness.fitnessApp.model.entity.Role;
import com.pronko.pets.fitness.fitnessApp.model.entity.User;
import com.pronko.pets.fitness.fitnessApp.model.request.AuthRequest;
import com.pronko.pets.fitness.fitnessApp.model.request.SignUpRequest;
import com.pronko.pets.fitness.fitnessApp.model.response.AuthResponse;
import com.pronko.pets.fitness.fitnessApp.repository.RoleRepository;
import com.pronko.pets.fitness.fitnessApp.repository.UserRepository;
import com.pronko.pets.fitness.fitnessApp.utils.JwtTokenUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenUtil jwtTokenUtil;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;


    @Override
    public ResponseEntity<?> login(AuthRequest authRequest) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authRequest.getUsername(),
                        authRequest.getPassword()
                )
        );

        var user = userRepository.findByUsername(authRequest.getUsername())
                .orElseThrow(
                        () -> new ApiException(HttpStatus.NOT_FOUND, "User with name  - "+ authRequest.getUsername() + " doesn't exist")
                );

        String token = jwtTokenUtil.generateAccessToken(user);
        AuthResponse authResponse = AuthResponse.builder()
                .username(user.getUsername())
                .token(token)
                .build();
        return ResponseEntity.ok(authResponse);
    }

    @Override
    @Transactional
    public ResponseEntity<?> signUp(SignUpRequest signUpRequest) {
        Optional<User> userExisting = userRepository.findByUsername(signUpRequest.getUsername());

        if (userExisting.isPresent()) {
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body("USER WITH USERNAME - " + userExisting.get().getUsername() + " EXIST");
        }

        if (signUpRequest.getPassword().equals(signUpRequest.getPasswordConfirm())) {
            User user = userMapper.signUpRequestToEntity(signUpRequest);
            Role defaultRole = roleRepository.findByName("USER").orElseThrow(
                    () -> new ApiException(HttpStatus.NOT_FOUND, "Role with name  - 'USER' doesn't exist")
            );
            String passwordEncode = passwordEncoder.encode(user.getPassword());
            user.setPassword(passwordEncode);
            user.setRoles(Set.of(defaultRole));
            userRepository.save(user);

            String token = jwtTokenUtil.generateAccessToken(user);
            AuthResponse authResponse = AuthResponse.builder()
                    .username(user.getUsername())
                    .token(token)
                    .build();
            return ResponseEntity.ok(authResponse);

        } else {
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body("Passwords are not matched");
        }
    }
}
