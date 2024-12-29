package com.example.kanbansystem.Service;

import com.example.kanbansystem.Repository.UserRepository;
import com.example.kanbansystem.Response.AuthenticationRequest;
import com.example.kanbansystem.Response.AuthenticationResponse;
import com.example.kanbansystem.entities.User;
import com.example.kanbansystem.service.authService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.BadCredentialsException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TestAuthService {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private authService authService;

    @Test
    public void testRegister_Success() {
        User mockUser = new User();
        mockUser.setUsername("testuser");
        mockUser.setPassword("password123");
        mockUser.setEmail("testuser@example.com");

        when(userRepository.save(any(User.class))).thenReturn(mockUser);

        AuthenticationResponse response = authService.register(mockUser);

        assertNotNull(response);
        assertEquals("User registered successfully", response.getMessage());
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    public void testAuthenticate_Success() {
        AuthenticationRequest request = new AuthenticationRequest();
        request.setUsername("testuser");
        request.setPassword("password123");

        User mockUser = new User();
        mockUser.setUsername("testuser");
        mockUser.setPassword("password123");

        when(userRepository.findByUsername("testuser")).thenReturn(mockUser);

        AuthenticationResponse response = authService.authenticate(request);

        assertNotNull(response);
        assertEquals("User authenticated successfully", response.getMessage());
        assertEquals(mockUser, response.getUser());
        verify(userRepository, times(1)).findByUsername("testuser");
    }

    @Test
    public void testAuthenticate_InvalidPassword() {
        AuthenticationRequest request = new AuthenticationRequest();
        request.setUsername("testuser");
        request.setPassword("wrongpassword");

        User mockUser = new User();
        mockUser.setUsername("testuser");
        mockUser.setPassword("password123");

        when(userRepository.findByUsername("testuser")).thenReturn(mockUser);

        BadCredentialsException exception = assertThrows(BadCredentialsException.class, () -> {
            authService.authenticate(request);
        });

        assertEquals("Invalid email or password", exception.getMessage());
        verify(userRepository, times(1)).findByUsername("testuser");
    }

    @Test
    public void testAuthenticate_UserNotFound() {
        AuthenticationRequest request = new AuthenticationRequest();
        request.setUsername("nonexistentuser");
        request.setPassword("password123");

        when(userRepository.findByUsername("nonexistentuser")).thenReturn(null);

        BadCredentialsException exception = assertThrows(BadCredentialsException.class, () -> {
            authService.authenticate(request);
        });

        assertEquals("Invalid email or password", exception.getMessage());
        verify(userRepository, times(1)).findByUsername("nonexistentuser");
    }

}
