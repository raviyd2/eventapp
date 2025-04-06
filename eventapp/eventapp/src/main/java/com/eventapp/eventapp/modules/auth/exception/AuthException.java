package com.eventapp.eventapp.modules.auth.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class AuthException extends RuntimeException {

    public AuthException(String message) {
        super(message);
    }

    public AuthException(String message, Throwable cause) {
        super(message, cause);
    }

    // Specific exception types
    public static class InvalidCredentialsException extends AuthException {
        public InvalidCredentialsException() {
            super("Invalid email or password");
        }
    }

    public static class EmailExistsException extends AuthException {
        public EmailExistsException() {
            super("Email already in use");
        }
    }

    public static class AccountDisabledException extends AuthException {
        public AccountDisabledException() {
            super("Account is disabled");
        }
    }
}