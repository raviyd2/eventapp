package com.eventapp.eventapp.config;

import com.eventapp.eventapp.modules.auth.model.User;
import com.eventapp.eventapp.modules.auth.model.UserPrincipal;
import com.eventapp.eventapp.modules.auth.repository.UserRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
@Component
@RequiredArgsConstructor
public class VendorSecurity {
    private final UserRepository userRepository;

    public boolean checkVendorId(Authentication authentication, Long vendorId) {
        String email = authentication.getName();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return user.getId().equals(vendorId);
    }
}