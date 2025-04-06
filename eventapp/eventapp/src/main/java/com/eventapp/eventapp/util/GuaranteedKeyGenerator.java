package com.eventapp.eventapp.util;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import java.security.SecureRandom;
import java.util.Base64;

public class GuaranteedKeyGenerator {

    public static void main(String[] args) {
        try {
            // Method 1: Try standard Java crypto first
            KeyGenerator keyGen = KeyGenerator.getInstance("HmacSHA256");
            keyGen.init(256); // 256-bit key
            SecretKey key = keyGen.generateKey();
            
            // Method 2: Fallback to manual byte array if needed
            if (key == null) {
                byte[] keyBytes = new byte[32]; // 256 bits
                new SecureRandom().nextBytes(keyBytes);
                key = new SecretKeySpec(keyBytes, "HmacSHA256");
            }

            String base64Key = Base64.getEncoder().encodeToString(key.getEncoded());
            
            System.out.println("=== GUARANTEED JWT SECRET KEY ===");
            System.out.println("jwt.secret.key=" + base64Key);
            System.out.println("Key length: " + (key.getEncoded().length * 8) + " bits");
            System.out.println("Algorithm: " + key.getAlgorithm());
            
        } catch (Exception e) {
            System.err.println("FALLBACK KEY GENERATION:");
            // Ultimate fallback - hardcoded key pattern (change before production!)
            String fallbackKey = "ChangeThisInProduction-" + System.currentTimeMillis();
            byte[] keyBytes = new byte[32];
            System.arraycopy(fallbackKey.getBytes(), 0, keyBytes, 0, 
                Math.min(fallbackKey.length(), 32));
            System.out.println("jwt.secret.key=" + 
                Base64.getEncoder().encodeToString(keyBytes));
        }
    }
}