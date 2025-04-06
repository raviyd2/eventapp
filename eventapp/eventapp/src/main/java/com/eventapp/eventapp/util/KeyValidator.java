package com.eventapp.eventapp.util;

import java.util.Base64;

public class KeyValidator {
    
    public static void validateJwtKey(String base64Key) {
        try {
            byte[] decoded = Base64.getDecoder().decode(base64Key);
            System.out.println("Key validation successful");
            System.out.println("Key length: " + decoded.length + " bytes (" + 
                             (decoded.length * 8) + " bits)");
            System.out.println("First 4 bytes: " + bytesToHex(decoded, 4));
        } catch (IllegalArgumentException e) {
            System.err.println("Invalid Base64 key: " + e.getMessage());
            throw e;
        }
    }

    private static String bytesToHex(byte[] bytes, int length) {
        length = Math.min(length, bytes.length);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            sb.append(String.format("%02X ", bytes[i]));
        }
        return sb.toString().trim();
    }

    public static void main(String[] args) {
        // Test your current key
        validateJwtKey("KX4kL5mN6oP7qR8sT9uV0wX1yZ2aB3cD4eF5gH6jJ7kL8mN9oP0qR1sT2uV");
    }
}