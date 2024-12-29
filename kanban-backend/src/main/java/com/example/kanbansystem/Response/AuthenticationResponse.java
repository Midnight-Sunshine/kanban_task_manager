package com.example.kanbansystem.Response;

import com.example.kanbansystem.entities.User;

public class AuthenticationResponse {

    private User user;

    private String message;

    // Default Constructor
    public AuthenticationResponse() {
    }

    // Parameterized Constructor
    public AuthenticationResponse(User user, String message) {
        this.user = user;
        this.message = message;
    }

    // Builder Pattern
    public static Builder builder() {
        return new Builder();
    }

    // Getters and Setters
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    // toString Method
    @Override
    public String toString() {
        return "AuthenticationResponse{" +
                "user=" + user +
                ", message='" + message + '\'' +
                '}';
    }

    // Builder Class
    public static class Builder {
        private User user;
        private String message;

        public Builder user(User user) {
            this.user = user;
            return this;
        }

        public Builder message(String message) {
            this.message = message;
            return this;
        }

        public AuthenticationResponse build() {
            return new AuthenticationResponse(user, message);
        }
    }
}
