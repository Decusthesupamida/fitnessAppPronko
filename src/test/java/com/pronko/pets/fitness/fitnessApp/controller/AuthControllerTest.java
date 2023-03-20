package com.pronko.pets.fitness.fitnessApp.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pronko.pets.fitness.fitnessApp.mapper.UserMapper;
import com.pronko.pets.fitness.fitnessApp.model.entity.Role;
import com.pronko.pets.fitness.fitnessApp.model.entity.User;
import com.pronko.pets.fitness.fitnessApp.model.enums.GenderEnum;
import com.pronko.pets.fitness.fitnessApp.model.request.AuthRequest;
import com.pronko.pets.fitness.fitnessApp.model.request.SignUpRequest;
import com.pronko.pets.fitness.fitnessApp.repository.RoleRepository;
import com.pronko.pets.fitness.fitnessApp.repository.UserRepository;
import lombok.SneakyThrows;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Set;

import static com.pronko.pets.fitness.fitnessApp.controller.AuthController.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@AutoConfigureMockMvc
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
@ExtendWith({SpringExtension.class})
//@Testcontainers
//@ContextConfiguration(initializers = {
//        PostgresInit.class,
//})
@Transactional
 public class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserMapper userMapper;

    @MockBean
    private AuthenticationManager authenticationManager;

    private static final String CONST_PASSWORD = "test1234";
    private Set<Role> rolesUser;
    private User testUser;


    @BeforeEach
    void setUp() {
        this.rolesUser = Set.of(
                createRole("ADMINTEST")
        );
        this.testUser = User.builder()
                .username("testLogin@gmail.com")
                .firstname("test")
                .lastname("test")
                .gender(GenderEnum.MALE)
                .roles(rolesUser)
                .birthdate(LocalDate.of(1999, 4, 23))
                .password(CONST_PASSWORD)
                .build();
        roleRepository.saveAll(rolesUser);
        this.testUser = userRepository.save(testUser);
    }

    @AfterEach
    void setDown() {
        resetDatabase();
    }

    @Test
    @SneakyThrows
    public void testSignUp_Success() {
        SignUpRequest signUpRequest = SignUpRequest.builder()
                .username("test@gmail.com")
                .birthdate(LocalDate.of(1999, 4, 23))
                .firstname("test")
                .lastname("test")
                .gender(GenderEnum.MALE)
                .password(CONST_PASSWORD)
                .passwordConfirm(CONST_PASSWORD)
                .build();

        mockMvc.perform(post(PATH + SIGNUP)
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(signUpRequest)))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @SneakyThrows
    public void testSignUp_ErrorPassword() {
        SignUpRequest signUpRequest = SignUpRequest.builder()
                .username("test@mail.com")
                .birthdate(LocalDate.of(1999, 4, 23))
                .lastname("test")
                .firstname("test")
                .gender(GenderEnum.MALE)
                .password(CONST_PASSWORD)
                .passwordConfirm(CONST_PASSWORD + "1")
                .build();

        mockMvc.perform(post(PATH + SIGNUP)
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(signUpRequest)))
                .andExpect(status().is4xxClientError())
                .andDo(print());
    }

    @Test
    @SneakyThrows
    public void testSignUp_ErrorUserExist() {
        SignUpRequest signUpRequest = SignUpRequest.builder()
                .username("testtesttest@mail.com")
                .birthdate(LocalDate.of(1999, 4, 23))
                .firstname("test")
                .lastname("test")
                .gender(GenderEnum.MALE)
                .password(CONST_PASSWORD)
                .passwordConfirm(CONST_PASSWORD)
                .build();

        User user = userMapper.signUpRequestToEntity(signUpRequest);
        userRepository.save(user);

        mockMvc.perform(post(PATH + SIGNUP)
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(signUpRequest)))
                .andExpect(status().isConflict())
                .andDo(print());
    }

    @Test
    @SneakyThrows
    public void testLogin_Success() {
        AuthRequest authRequest = userMapper.entityToAuthRequest(this.testUser);

        mockMvc.perform(post(PATH + LOGIN)
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(authRequest)))
                .andExpect(status().isOk())
                .andDo(print());
    }

    public void resetDatabase() {
        userRepository.deleteAllInBatch();
        roleRepository.deleteAllInBatch();
    }

    private Role createRole(String roleName) {
        Role role = new Role(roleName);
        roleRepository.save(role);
        return role;
    }
}
