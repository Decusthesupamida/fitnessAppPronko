package com.pronko.pets.fitness.fitnessApp.utils;

import org.testcontainers.utility.TestcontainersConfiguration;

public class TestcontainersUtils {
    public static String resolveImage(String imageName, String defaultImage) {
        return TestcontainersConfiguration.getInstance().getEnvVarOrProperty(imageName, defaultImage);
    }

    private TestcontainersUtils() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }
}
