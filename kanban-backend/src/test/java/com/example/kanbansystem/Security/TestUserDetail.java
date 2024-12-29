package com.example.kanbansystem.Security;

import com.example.kanbansystem.Repository.UserRepository;
import com.example.kanbansystem.entities.User;
import com.example.kanbansystem.security.App_user;
import com.example.kanbansystem.security.MyuserDetailsService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MyuserDetailsServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private MyuserDetailsService myuserDetailsService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testLoadUserByUsername_UserExists() {
        User user = new User();
        user.setUsername("testuser");
        user.setPassword("password123");
        user.setRoles("ROLE_USER");

        when(userRepository.findByUsername("testuser")).thenReturn(user);

        App_user appUser = (App_user) myuserDetailsService.loadUserByUsername("testuser");

        assertEquals("testuser", appUser.getUsername());
        assertEquals("password123", appUser.getPassword());
        assertEquals(1, appUser.getAuthorities().size());
        assertTrue(appUser.getAuthorities().stream()
                .anyMatch(auth -> auth.getAuthority().equals("ROLE_USER")));

        verify(userRepository, times(1)).findByUsername("testuser");
    }

    @Test
    void testLoadUserByUsername_UserNotFound() {
        when(userRepository.findByUsername("nonexistentuser")).thenReturn(null);

        Exception exception = assertThrows(UsernameNotFoundException.class, () ->
                myuserDetailsService.loadUserByUsername("nonexistentuser"));

        assertEquals("User not found with username: nonexistentuser", exception.getMessage());

        verify(userRepository, times(1)).findByUsername("nonexistentuser");
    }

    @Test
    void testAddNewUser() {
        User user = new User();
        user.setUsername("newuser");
        user.setPassword("rawpassword");
        user.setEmail("newuser@example.com");

        when(passwordEncoder.encode("rawpassword")).thenReturn("encodedpassword");
        when(userRepository.save(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));

        User savedUser = myuserDetailsService.addNewUser(user);

        assertEquals("newuser", savedUser.getUsername());
        assertEquals("encodedpassword", savedUser.getPassword());
        assertEquals("newuser@example.com", savedUser.getEmail());
        assertTrue(savedUser.isActive());
        assertEquals("ROLE_USER", savedUser.getRoles());

        verify(passwordEncoder, times(1)).encode("rawpassword");
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void testGetAllUsers() {
        List<User> users = List.of(
                new User("user1", "pass1", "user1@example.com", true, "ROLE_USER"),
                new User("user2", "pass2", "user2@example.com", true, "ROLE_ADMIN")
        );

        when(userRepository.findAll()).thenReturn(users);

        List<User> retrievedUsers = myuserDetailsService.getAllUsers();

        assertEquals(2, retrievedUsers.size());
        assertEquals("user1", retrievedUsers.get(0).getUsername());
        assertEquals("user2", retrievedUsers.get(1).getUsername());

        verify(userRepository, times(1)).findAll();
    }

    @Test
    void testFindUserByUsername() {
        User user = new User("finduser", "findpass", "finduser@example.com", true, "ROLE_USER");

        when(userRepository.findByUsername("finduser")).thenReturn(user);

        User foundUser = myuserDetailsService.findUserByUsername("finduser");

        assertEquals("finduser", foundUser.getUsername());
        assertEquals("finduser@example.com", foundUser.getEmail());

        verify(userRepository, times(1)).findByUsername("finduser");
    }

    @Test
    void testGetUserById_UserExists() {
        User user = new User("userbyid", "passid", "userbyid@example.com", true, "ROLE_USER");

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        Optional<User> foundUser = myuserDetailsService.getUserById(1L);

        assertTrue(foundUser.isPresent());
        assertEquals("userbyid", foundUser.get().getUsername());

        verify(userRepository, times(1)).findById(1L);
    }

    @Test
    void testGetUserById_UserNotFound() {
        when(userRepository.findById(2L)).thenReturn(Optional.empty());

        Optional<User> foundUser = myuserDetailsService.getUserById(2L);

        assertTrue(foundUser.isEmpty());

        verify(userRepository, times(1)).findById(2L);
    }
}
