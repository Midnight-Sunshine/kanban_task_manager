package com.example.kanbansystem.Controller;

import com.example.kanbansystem.Response.AuthenticationRequest;
import com.example.kanbansystem.Response.AuthenticationResponse;
import com.example.kanbansystem.controller.UserController;
import com.example.kanbansystem.entities.User;
import com.example.kanbansystem.security.MyuserDetailsService;
import com.example.kanbansystem.service.authService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TestUserController {

    @Mock
    private MyuserDetailsService myuserDetailsService;

    @Mock
    private authService service;

    @InjectMocks
    private UserController userController;

    @Test
    public void testAddUser() {
        User mockUser = new User();
        mockUser.setUsername("testuser");

        when(myuserDetailsService.addNewUser(mockUser)).thenReturn(mockUser);

        String response = userController.addUser(mockUser);

        assertEquals("User added successfully!", response);
        verify(myuserDetailsService, times(1)).addNewUser(mockUser);
    }



    @Test
    public void testGetAllUsers() {
        List<User> mockUsers = new ArrayList<>();
        mockUsers.add(new User());
        mockUsers.add(new User());

        when(myuserDetailsService.getAllUsers()).thenReturn(mockUsers);

        List<User> response = userController.getAllUsers();

        assertEquals(mockUsers.size(), response.size());
        assertEquals(mockUsers, response);
        verify(myuserDetailsService, times(1)).getAllUsers();
    }

    @Test
    public void testGetUserByUsername_Found() {
        String username = "testuser";
        User mockUser = new User();
        mockUser.setUsername(username);

        when(myuserDetailsService.findUserByUsername(username)).thenReturn(mockUser);

        ResponseEntity<User> responseEntity = userController.getUserByUsername(username);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(mockUser, responseEntity.getBody());
        verify(myuserDetailsService, times(1)).findUserByUsername(username);
    }

    @Test
    public void testGetUserByUsername_NotFound() {
        String username = "nonexistent";

        when(myuserDetailsService.findUserByUsername(username)).thenReturn(null);

        ResponseEntity<User> responseEntity = userController.getUserByUsername(username);

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        verify(myuserDetailsService, times(1)).findUserByUsername(username);
    }

    @Test
    public void testAuthenticate_Success() {
        AuthenticationRequest request = new AuthenticationRequest();
        request.setUsername("testuser");
        request.setPassword("password");

        AuthenticationResponse mockResponse = new AuthenticationResponse();
        when(service.authenticate(any(AuthenticationRequest.class))).thenReturn(mockResponse);

        ResponseEntity<AuthenticationResponse> responseEntity = userController.authenticate(request);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(mockResponse, responseEntity.getBody());
        verify(service, times(1)).authenticate(request);
    }

    @Test
    public void testRegister_Success() {
        User request = new User();
        request.setUsername("testuser");

        AuthenticationResponse mockResponse = new AuthenticationResponse();
        when(service.register(any(User.class))).thenReturn(mockResponse);

        ResponseEntity<AuthenticationResponse> responseEntity = userController.register(request);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(mockResponse, responseEntity.getBody());
        verify(service, times(1)).register(request);
    }
}
