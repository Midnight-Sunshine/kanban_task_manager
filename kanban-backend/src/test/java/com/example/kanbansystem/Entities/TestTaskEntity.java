package com.example.kanbansystem.Entities;

import org.junit.jupiter.api.Test;

import com.example.kanbansystem.entities.Board;
import com.example.kanbansystem.entities.Sprint;
import com.example.kanbansystem.entities.Task;
import com.example.kanbansystem.entities.TaskStatus;
import com.example.kanbansystem.entities.User;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.validation.ConstraintViolation;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class TaskTest {

    @Test
    void testDefaultConstructor() {
        Task task = new Task();
        assertNull(task.getId());
        assertNull(task.getName());
        assertNull(task.getDescription());
        assertNull(task.getStatus());
        assertNull(task.getEstimation());
        assertNull(task.getBoard());
        assertNull(task.getUsers());
        assertNull(task.getSprints());
    }

    @Test
    void testParameterizedConstructor() {
        Board board = new Board();
        User user = new User();
        Sprint sprint = new Sprint();

        Task task = new Task(1L, "Task Name", "Task Description", TaskStatus.TODO, 3, board, List.of(user), List.of(sprint));

        assertEquals(1L, task.getId());
        assertEquals("Task Name", task.getName());
        assertEquals("Task Description", task.getDescription());
        assertEquals(TaskStatus.TODO, task.getStatus());
        assertEquals(3, task.getEstimation());
        assertEquals(board, task.getBoard());
        assertNotNull(task.getUsers());
        assertEquals(1, task.getUsers().size());
        assertNotNull(task.getSprints());
        assertEquals(1, task.getSprints().size());
    }

    @Test
    void testSettersAndGetters() {
        Task task = new Task();

        task.setId(10L);
        assertEquals(10L, task.getId());

        task.setName("Updated Task");
        assertEquals("Updated Task", task.getName());

        task.setDescription("Updated Description");
        assertEquals("Updated Description", task.getDescription());

        task.setStatus(TaskStatus.IN_PROGRESS);
        assertEquals(TaskStatus.IN_PROGRESS, task.getStatus());

        task.setEstimation(5);
        assertEquals(5, task.getEstimation());

        Board board = new Board();
        task.setBoard(board);
        assertEquals(board, task.getBoard());

        User user = new User();
        task.setUsers(List.of(user));
        assertNotNull(task.getUsers());
        assertEquals(1, task.getUsers().size());

        Sprint sprint = new Sprint();
        task.setSprints(List.of(sprint));
        assertNotNull(task.getSprints());
        assertEquals(1, task.getSprints().size());
    }

    @Test
    void testValidation() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        // Create a valid Task object
        Task task = new Task(1L, "Valid Task", "Valid Description", TaskStatus.TODO, 5, new Board(), List.of(), List.of());

        // Validate the Task object
        Set<ConstraintViolation<Task>> violations = validator.validate(task);

        // Assert no violations
        assertTrue(violations.isEmpty());
    }

    @Test
    void testInvalidTask() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        // Create an invalid Task object
        Task task = new Task();
        task.setName(""); // Name should not be blank
        task.setDescription(""); // Description should not be blank

        // Validate the Task object
        Set<ConstraintViolation<Task>> violations = validator.validate(task);

        // Assert violations are found
        assertFalse(violations.isEmpty());

        // Print violations for debugging
        violations.forEach(v -> System.out.println(v.getPropertyPath() + ": " + v.getMessage()));
    }

    @Test
    void testEnums() {
        Task task = new Task();
        task.setStatus(TaskStatus.DONE);
        assertEquals(TaskStatus.DONE, task.getStatus());
    }
}
