package com.example.kanbansystem.Entities;

import org.junit.jupiter.api.Test;

import com.example.kanbansystem.entities.User;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Collections;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    @Test
    void testDefaultConstructor() {
        User user = new User();
        assertNull(user.getId());
        assertNull(user.getUsername());
        assertNull(user.getPassword());
        assertNull(user.getEmail());
        assertFalse(user.isActive());
        assertNull(user.getRoles());
        assertNull(user.getBoards());
        assertNull(user.getTasks());
    }

    @Test
    void testParameterizedConstructor() {
        User user = new User(1L, "testuser", "password123", "test@example.com", true, "ROLE_USER",
                Collections.emptyList(), Collections.emptyList());

        assertEquals(1L, user.getId());
        assertEquals("testuser", user.getUsername());
        assertEquals("password123", user.getPassword());
        assertEquals("test@example.com", user.getEmail());
        assertTrue(user.isActive());
        assertEquals("ROLE_USER", user.getRoles());
        assertTrue(user.getBoards().isEmpty());
        assertTrue(user.getTasks().isEmpty());
    }

    @Test
    void testConstructorWithoutIdBoardsTasks() {
        User user = new User("testuser", "password123", "test@example.com", true, "ROLE_ADMIN");

        assertNull(user.getId());
        assertEquals("testuser", user.getUsername());
        assertEquals("password123", user.getPassword());
        assertEquals("test@example.com", user.getEmail());
        assertTrue(user.isActive());
        assertEquals("ROLE_ADMIN", user.getRoles());
        assertNull(user.getBoards());
        assertNull(user.getTasks());
    }

    @Test
    void testSettersAndGetters() {
        User user = new User();

        user.setId(10L);
        assertEquals(10L, user.getId());

        user.setUsername("newuser");
        assertEquals("newuser", user.getUsername());

        user.setPassword("newpassword");
        assertEquals("newpassword", user.getPassword());

        user.setEmail("newemail@example.com");
        assertEquals("newemail@example.com", user.getEmail());

        user.setActive(true);
        assertTrue(user.isActive());

        user.setRoles("ROLE_ADMIN");
        assertEquals("ROLE_ADMIN", user.getRoles());

        user.setBoards(Collections.emptyList());
        assertTrue(user.getBoards().isEmpty());

        user.setTasks(Collections.emptyList());
        assertTrue(user.getTasks().isEmpty());
    }

    @Test
    void testToString() {
        User user = new User("testuser", "password123", "test@example.com", true, "ROLE_USER");
        user.setId(1L);

        String expectedString = "User{id=1, username='testuser', password='password123', email='test@example.com', active=true, roles='ROLE_USER'}";
        assertEquals(expectedString, user.toString());
    }

    @Test
    void testValidation() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        User user = new User(); // Missing required fields
        Set<ConstraintViolation<User>> violations = validator.validate(user);

        assertFalse(violations.isEmpty());
    }

}
