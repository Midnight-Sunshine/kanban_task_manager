package com.example.kanbansystem.Security;

import com.example.kanbansystem.entities.User;
import com.example.kanbansystem.security.App_user;

import org.junit.jupiter.api.Test;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class App_userTest {

    @Test
    void testDefaultConstructor() {
        App_user appUser = new App_user();

        assertNull(appUser.getUsername());
        assertNull(appUser.getPassword());
        assertNull(appUser.getEmail());
        assertNull(appUser.getAuthorities());
        assertFalse(appUser.isEnabled());
    }

    @Test
    void testParameterizedConstructor() {
        // Create a User object with matching username
        User user = new User();
        user.setUsername("testuser");  // Ensure the username matches the expected value
        user.setPassword("password123");
        user.setEmail("test@example.com");
        user.setActive(true);
        user.setRoles("ROLE_USER,ROLE_ADMIN");

        // Create an App_user using the User object
        App_user appUser = new App_user(user);

        // Assertions
        assertEquals("testuser", appUser.getUsername()); // This should now match
        assertEquals("password123", appUser.getPassword());
        assertTrue(appUser.isEnabled());
        assertEquals(2, appUser.getAuthorities().size());
        assertTrue(appUser.getAuthorities().stream()
                .anyMatch(auth -> auth.getAuthority().equals("ROLE_USER")));
        assertTrue(appUser.getAuthorities().stream()
                .anyMatch(auth -> auth.getAuthority().equals("ROLE_ADMIN")));
    }


    @Test
    void testUserConstructor() {
        User user = new User();
        user.setUsername("user123");
        user.setPassword("pass123");
        user.setEmail("user123@example.com");
        user.setActive(true);
        user.setRoles("ROLE_USER,ROLE_ADMIN");

        App_user appUser = new App_user(user);

        assertEquals("user123", appUser.getUsername());
        assertEquals("pass123", appUser.getPassword());
        assertEquals("user123@example.com", appUser.getEmail());
        assertTrue(appUser.isEnabled());
        assertEquals(2, appUser.getAuthorities().size());
        assertTrue(appUser.getAuthorities().stream()
                .anyMatch(auth -> auth.getAuthority().equals("ROLE_USER")));
        assertTrue(appUser.getAuthorities().stream()
                .anyMatch(auth -> auth.getAuthority().equals("ROLE_ADMIN")));
    }

    @Test
    void testSetters() {
        App_user appUser = new App_user();

        appUser.setUsername("newuser");
        assertEquals("newuser", appUser.getUsername());

        appUser.setPassword("newpass");
        assertEquals("newpass", appUser.getPassword());

        appUser.setEmail("newemail@example.com");
        assertEquals("newemail@example.com", appUser.getEmail());

        appUser.setActive(true);
        assertTrue(appUser.isEnabled());

        List<GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority("ROLE_MODERATOR"));
        appUser.setAuthorities(authorities);
        assertEquals(1, appUser.getAuthorities().size());
        assertTrue(appUser.getAuthorities().stream()
                .anyMatch(auth -> auth.getAuthority().equals("ROLE_MODERATOR")));
    }

    @Test
    void testAccountStatusMethods() {
        // Create a User object with active set to true
        User user = new User();
        user.setUsername("testuser");
        user.setPassword("password123");
        user.setActive(true); // Ensure active is true
        user.setRoles("ROLE_USER");

        // Create an App_user using the User object
        App_user appUser = new App_user(user);

        // Test account status methods
        assertTrue(appUser.isAccountNonExpired());
        assertTrue(appUser.isAccountNonLocked());
        assertTrue(appUser.isCredentialsNonExpired());
        assertTrue(appUser.isEnabled()); // This should now return true
    }


    @Test
    void testGrantedAuthorities() {
        User user = new User();
        user.setRoles("ROLE_USER,ROLE_MANAGER");

        App_user appUser = new App_user(user);

        List<GrantedAuthority> authorities = (List<GrantedAuthority>) appUser.getAuthorities();

        assertEquals(2, authorities.size());
        assertTrue(authorities.stream()
                .anyMatch(auth -> auth.getAuthority().equals("ROLE_USER")));
        assertTrue(authorities.stream()
                .anyMatch(auth -> auth.getAuthority().equals("ROLE_MANAGER")));
    }
}
