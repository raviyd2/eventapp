package com.eventapp.eventapp.util;

import javax.crypto.SecretKey;
import io.jsonwebtoken.security.Keys;
import java.util.Base64;

public class JwtKeyValidator {
    
    public static void main(String[] args) {
        String testKey = "KX4kL5mN6oP7qR8sT9uV0wX1yZ2aB3cD4eF5gH6jJ7kL8mN9oP0qR1sT2uV";
        
        try {
            System.out.println("=== JWT KEY VALIDATION ===");
            System.out.println("Original key: " + testKey.substring(0, 4) + "..." + testKey.substring(testKey.length()-4));
            
            // 1. Validate Base64
            byte[] decodedBytes = Base64.getDecoder().decode(testKey);
            System.out.println("✅ Base64 validation passed");
            
            // 2. Validate key length
            if (decodedBytes.length >= 32) {
                System.out.println("✅ Key length OK: " + decodedBytes.length + " bytes (" + (decodedBytes.length*8) + " bits)");
            } else {
                System.out.println("❌ Key too short: " + decodedBytes.length + " bytes");
            }
            
            // 3. Create signing key
            SecretKey key = Keys.hmacShaKeyFor(decodedBytes);
            System.out.println("✅ Key creation successful");
            System.out.println("Algorithm: " + key.getAlgorithm());
            
        } catch (IllegalArgumentException e) {
            System.out.println("❌ Validation failed: " + e.getMessage());
            e.printStackTrace();
        }
    }
}