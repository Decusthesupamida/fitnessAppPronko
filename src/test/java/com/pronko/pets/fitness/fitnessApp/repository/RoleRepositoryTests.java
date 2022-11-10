package com.pronko.pets.fitness.fitnessApp.repository;

import com.github.dockerjava.api.exception.NotFoundException;
import com.pronko.pets.fitness.fitnessApp.entity.Role;
import com.pronko.pets.fitness.fitnessApp.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
class RoleRepositoryTests {

    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private UserRepository userRepository;

    @Test
    public void testCreateRoles() {
        Role admin = new Role("ROLE_ADMIN");
        Role editor = new Role("ROLE_EDITOR");
        Role customer = new Role("ROLE_CUSTOMER");


        roleRepository.saveAll(List.of(
                admin, editor, customer
        ));

        long countRoles = roleRepository.count();
        assertEquals(3, countRoles);
    }

    @Test
    public void testListRoles() {
        List<Role> roles = roleRepository.findAll();
        assertThat(roles.size()).isGreaterThan(0);
    }

    @Test
    public void testAssignRolesToUser() {
        Long userId = 7L;
        Long roleId = 3L;
        Role roleEditor = roleRepository.findById(roleId).orElseThrow(() ->
                new NotFoundException("not found role by id : " + roleId)
                );
        User user = userRepository.findById(userId).orElseThrow(() ->
                new NotFoundException("not found user by id : " + userId)
                );
        user.addRole(roleEditor);

        User userSaved = userRepository.save(user);

        assertThat(userSaved.getRoles()).hasSize(2);


    }

}
