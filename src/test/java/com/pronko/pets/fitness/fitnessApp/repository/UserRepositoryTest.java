package com.pronko.pets.fitness.fitnessApp.repository;

import com.pronko.pets.fitness.fitnessApp.model.entity.User;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.annotation.Rollback;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    @SneakyThrows
    public void testCreateUser() {
        User user = new User();
        user.setFirstname("john");
        user.setUsername("john@mail.com");
        user.setLastname("testlast");
        String password = new BCryptPasswordEncoder().encode("john1234");
        user.setPassword(password);

        User savedUser = userRepository.save(user);

        assertThat(savedUser).isNotNull();
        assertThat(savedUser.getId()).isGreaterThan(0);
    }

}
