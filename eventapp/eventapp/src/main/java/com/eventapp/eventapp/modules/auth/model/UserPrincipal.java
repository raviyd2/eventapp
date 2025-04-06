package com.eventapp.eventapp.modules.auth.model;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.Collection;
@Data
public class UserPrincipal implements UserDetails {
    private final Long id;  // This is critical
    private final String username;
    private final String password;
    private final Collection<? extends GrantedAuthority> authorities;

    // Implement all UserDetails methods below...
    @Override public boolean isAccountNonExpired() { return true; }
    @Override public boolean isAccountNonLocked() { return true; }
    @Override public boolean isCredentialsNonExpired() { return true; }
    @Override public boolean isEnabled() { return true; }
}