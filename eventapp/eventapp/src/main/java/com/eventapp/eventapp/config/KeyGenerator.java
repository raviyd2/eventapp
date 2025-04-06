package com.eventapp.eventapp.config;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import javax.crypto.SecretKey;
import java.util.Base64;

public class KeyGenerator {

    public static void main(String[] args) {
        try {
            // Generate a secure 256-bit key
            SecretKey key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
            
            // Convert to Base64
            String base64Key = Base64.getEncoder().encodeToString(key.getEncoded());
            
            System.out.println("Add this to your application.properties:");
            System.out.println("jwt.secret.key=" + base64Key);
            System.out.println("Key length: " + base64Key.length() + " characters");
            
        } catch (Exception e) {
            System.err.println("Key generation failed:");
            e.printStackTrace();
        }
    }
}