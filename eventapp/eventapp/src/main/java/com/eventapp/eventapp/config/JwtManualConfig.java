package com.eventapp.eventapp.config;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.crypto.SecretKey;
import java.util.Base64;

@Configuration
public class JwtManualConfig {

    @Bean
    public SecretKey jwtSecretKey() {
        // Use a known-good test key
        String testKey = "KX4kL5mN6oP7qR8sT9uV0wX1yZ2aB3cD4eF5gH6jJ7kL8mN9oP0qR1sT2uV";
        byte[] decoded = Base64.getDecoder().decode(testKey);
        return Keys.hmacShaKeyFor(decoded);
    }

    @Bean
    public JwtUtil jwtUtil() {
        return new JwtUtil(jwtSecretKey(), 86400000L);
    }
}