package com.example.kanbansystem.Response;

public class AuthenticationRequest {

    private String username;

    private String password;

    // Default Constructor
    public AuthenticationRequest() {
    }

    // Parameterized Constructor
    public AuthenticationRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }

    // Builder Pattern
    public static Builder builder() {
        return new Builder();
    }

    // Getters and Setters
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    // toString Method
    @Override
    public String toString() {
        return "AuthenticationRequest{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    // Builder Class
    public static class Builder {
        private String username;
        private String password;

        public Builder username(String username) {
            this.username = username;
            return this;
        }

        public Builder password(String password) {
            this.password = password;
            return this;
        }

        public AuthenticationRequest build() {
            return new AuthenticationRequest(username, password);
        }
    }
}
