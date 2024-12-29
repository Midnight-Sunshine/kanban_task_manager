package com.example.kanbansystem.service;

import com.example.kanbansystem.Repository.UserRepository;
import com.example.kanbansystem.Response.AuthenticationRequest;
import com.example.kanbansystem.Response.AuthenticationResponse;
import com.example.kanbansystem.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class authService {
    @Autowired
    private UserRepository repository;

    public AuthenticationResponse register(User user){
        user.setPassword(user.getPassword());
        user.setRoles("ROLE_ADMIN");
        user.setActive(true);
        repository.save(user);

        return AuthenticationResponse.builder()
                .message("User registered successfully")
                .build();
    }
    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        User user = repository.findByUsername(request.getUsername());
        if (user == null) { // Handle the case where the user is not found
            throw new BadCredentialsException("Invalid email or password");
        }
        if (!request.getPassword().equals(user.getPassword())) {
            throw new BadCredentialsException("Invalid email or password");
        }
        return AuthenticationResponse.builder()
                .message("User authenticated successfully")
                .user(user)
                .build();
    }

}
