package com.pronko.pets.fitness.fitnessApp.utils;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;


@NoArgsConstructor
@Slf4j
public class PostgresInit implements ApplicationContextInitializer<ConfigurableApplicationContext> {
    @Container
    private final PostgreSQLContainer postgres = new PostgreSQLContainer(TestcontainersUtils.resolveImage("postgres.container.image", "postgres:11.1"));


    public void initialize(@NotNull ConfigurableApplicationContext configurableApplicationContext) {
        this.postgres.start();
        TestPropertyValues testPropertyValues = TestPropertyValues.of("spring.datasource.url=" + this.postgres.getJdbcUrl(), "spring.datasource.username=" + this.postgres.getUsername(), "spring.datasource.password=" + this.postgres.getPassword());
        log.info("postgres jdbc url: {} username: {} password: {}", this.postgres.getJdbcUrl(), this.postgres.getUsername(), this.postgres.getPassword());
        testPropertyValues.applyTo(configurableApplicationContext);
    }
}
