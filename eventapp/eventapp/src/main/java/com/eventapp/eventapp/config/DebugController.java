package com.eventapp.eventapp.config;

import com.eventapp.eventapp.config.JwtUtil;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/debug")
public class DebugController {
    
    private final JwtUtil jwtUtil;
    
    public DebugController(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }
    
    @GetMapping("/jwt-status")
    public String getJwtStatus() {
        try {
            // Try to generate a test token
            String testToken = jwtUtil.generateToken(new TestUserDetails());
            return "JWT WORKING - Test token: " + testToken.substring(0, 20) + "...";
        } catch (Exception e) {
            return "JWT FAILED: " + e.getMessage();
        }
    }
    
    static class TestUserDetails extends org.springframework.security.core.userdetails.User {
        public TestUserDetails() {
            super("test", "test", List.of());
        }
    }
}