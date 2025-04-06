package com.eventapp.eventapp.util;

import io.jsonwebtoken.security.Keys;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

public class JwtKeyGenerator {

    public static void main(String[] args) {
        try {
            SecretKey key = generateKey();
            String base64Key = Base64.getEncoder().encodeToString(key.getEncoded());
            System.out.println("=== JWT SECRET KEY ===");
            System.out.println("Add to application.properties:");
            System.out.println("jwt.secret.key=" + base64Key);
            System.out.println("\nKey Length: 256-bit (32 bytes)");
            System.out.println("Base64 Length: " + base64Key.length() + " characters");
        } catch (Exception e) {
            System.err.println("Key generation failed. Manual solution:");
            System.err.println("1. Install OpenSSL (https://www.openssl.org/)");
            System.err.println("2. Run: openssl rand -base64 32");
            System.err.println("3. Copy output to application.properties");
        }
    }

    private static SecretKey generateKey() {
        // Method 1: Direct HMAC-SHA256 key generation
        try {
            return generateHmacSha256Key();
        } catch (Exception e) {
            System.err.println("Direct generation failed: " + e.getMessage());
        }

        // Method 2: Java Cryptography
        try {
            KeyGenerator keyGen = KeyGenerator.getInstance("HmacSHA256");
            keyGen.init(256);
            return keyGen.generateKey();
        } catch (NoSuchAlgorithmException e) {
            System.err.println("Java crypto failed: " + e.getMessage());
        }

        // Method 3: Manual fallback
        return generateManualKey();
    }

    private static SecretKey generateHmacSha256Key() {
        byte[] keyBytes = new byte[32]; // 256 bits
        new SecureRandom().nextBytes(keyBytes);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    private static SecretKey generateManualKey() {
        byte[] keyBytes = new byte[32];
        new SecureRandom().nextBytes(keyBytes);
        return new javax.crypto.spec.SecretKeySpec(keyBytes, "HmacSHA256");
    }
}