package com.example.kanbansystem.Entities;

import org.junit.jupiter.api.Test;

import com.example.kanbansystem.entities.Board;
import com.example.kanbansystem.entities.User;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.validation.ConstraintViolation;
import java.util.Date;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class BoardTest {

    @Test
    void testDefaultConstructor() {
        Board board = new Board();
        assertNull(board.getId());
        assertNull(board.getName());
        assertNull(board.getDescription());
        assertNull(board.getDateCreation());
        assertNull(board.getTasks());
        assertNull(board.getUser());
        assertNull(board.getUserId());
    }

    @Test
    void testParameterizedConstructor() {
        User user = new User(1L, "testuser", "password", "test@example.com", true, "ROLE_USER", null, null);
        Date now = new Date();
        Board board = new Board(1L, "Test Board", "Test Description", now, List.of(), user);

        assertEquals(1L, board.getId());
        assertEquals("Test Board", board.getName());
        assertEquals("Test Description", board.getDescription());
        assertEquals(now, board.getDateCreation());
        assertNotNull(board.getTasks());
        assertEquals(user, board.getUser());
    }

    @Test
    void testSettersAndGetters() {
        Board board = new Board();

        board.setId(10L);
        assertEquals(10L, board.getId());

        board.setName("Updated Board");
        assertEquals("Updated Board", board.getName());

        board.setDescription("Updated Description");
        assertEquals("Updated Description", board.getDescription());

        Date now = new Date();
        board.setDateCreation(now);
        assertEquals(now, board.getDateCreation());

        board.setTasks(List.of());
        assertNotNull(board.getTasks());

        User user = new User();
        user.setId(1L);
        board.setUser(user);
        assertEquals(user, board.getUser());

        board.setUserId(1L);
        assertEquals(1L, board.getUserId());
    }

    @Test
    void testToString() {
        Board board = new Board();
        board.setId(1L);
        board.setName("Board Name");
        board.setDescription("Board Description");
        board.setDateCreation(new Date());

        String toStringResult = board.toString();
        assertTrue(toStringResult.contains("id=1"));
        assertTrue(toStringResult.contains("name='Board Name'"));
        assertTrue(toStringResult.contains("description='Board Description'"));
    }

    @Test
    void testValidation() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        // Create a valid Board object
        User user = new User(1L, "testuser", "password", "test@example.com", true, "ROLE_USER", null, null);
        Board board = new Board(1L, "Valid Board", "Valid Description", new Date(), List.of(), user);

        // Validate the Board object
        Set<ConstraintViolation<Board>> violations = validator.validate(board);

        // Assert no violations
        assertTrue(violations.isEmpty());
    }

    @Test
    void testInvalidBoard() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        // Create an invalid Board object
        Board board = new Board();
        board.setName(""); // Name should not be blank
        board.setDescription(""); // Description should not be blank

        // Validate the Board object
        Set<ConstraintViolation<Board>> violations = validator.validate(board);

        // Assert violations are found
        assertFalse(violations.isEmpty());

        // Print violations for debugging
        violations.forEach(v -> System.out.println(v.getPropertyPath() + ": " + v.getMessage()));
    }
}
