package com.example.kanbansystem.Service;

import com.example.kanbansystem.Repository.UserRepository;
import com.example.kanbansystem.entities.User;
import com.example.kanbansystem.service.UserService;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TestUserService {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Test
    public void testSaveUser() {
        User mockUser = new User();
        mockUser.setUsername("testUser");
        mockUser.setPassword("password");
        mockUser.setEmail("test@example.com");
        mockUser.setActive(true);
        mockUser.setRoles("ROLE_USER");

        when(userRepository.save(any(User.class))).thenReturn(mockUser);

        User result = userService.saveUser(mockUser);

        assertEquals(mockUser, result);
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    public void testGetUserById_UserExists() {
        Long userId = 1L;
        User mockUser = new User();
        mockUser.setId(userId);
        when(userRepository.findById(userId)).thenReturn(Optional.of(mockUser));

        Optional<User> result = userService.getUserById(userId);

        assertTrue(result.isPresent());
        assertEquals(mockUser, result.get());
        verify(userRepository, times(1)).findById(userId);
    }

    @Test
    public void testGetUserById_UserDoesNotExist() {
        Long userId = 1L;
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        Optional<User> result = userService.getUserById(userId);

        assertTrue(result.isEmpty());
        verify(userRepository, times(1)).findById(userId);
    }

    @Test
    public void testGetUsersByIds() {
        List<Long> userIds = Arrays.asList(1L, 2L, 3L);
        List<User> mockUsers = Arrays.asList(new User(), new User(), new User());
        when(userRepository.findAllById(userIds)).thenReturn(mockUsers);

        List<User> result = userService.getUsersByIds(userIds);

        assertEquals(mockUsers.size(), result.size());
        assertEquals(mockUsers, result);
        verify(userRepository, times(1)).findAllById(userIds);
    }
}
