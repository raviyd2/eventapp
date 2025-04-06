package com.eventapp.eventapp.config;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration
public class ConfigValidator {

    private final Environment env;

    public ConfigValidator(Environment env) {
        this.env = env;
    }

    @PostConstruct
    public void validateConfig() {
        checkProperty("jwt.secret.key");
        checkProperty("spring.datasource.url");
        // Add other critical properties to check
    }

    private void checkProperty(String propertyName) {
        if (env.getProperty(propertyName) == null) {
            throw new IllegalStateException("Required property '" + propertyName + "' is not configured");
        }
    }
}