package com.example.kanbansystem.Entities;

import org.junit.jupiter.api.Test;

import com.example.kanbansystem.entities.Sprint;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.validation.ConstraintViolation;
import java.util.Date;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class SprintTest {

    @Test
    void testDefaultConstructor() {
        Sprint sprint = new Sprint();
        assertEquals(0, sprint.getId());
        assertNull(sprint.getName());
        assertNull(sprint.getStartDate());
        assertNull(sprint.getEndDate());
        assertNull(sprint.getTasks());
    }

    @Test
    void testParameterizedConstructor() {
        Date startDate = new Date();
        Date endDate = new Date();
        Sprint sprint = new Sprint(1L, "Sprint 1", startDate, endDate, List.of());

        assertEquals(1L, sprint.getId());
        assertEquals("Sprint 1", sprint.getName());
        assertEquals(startDate, sprint.getStartDate());
        assertEquals(endDate, sprint.getEndDate());
        assertNotNull(sprint.getTasks());
        assertTrue(sprint.getTasks().isEmpty());
    }

    @Test
    void testSettersAndGetters() {
        Sprint sprint = new Sprint();

        sprint.setId(10L);
        assertEquals(10L, sprint.getId());

        sprint.setName("Updated Sprint");
        assertEquals("Updated Sprint", sprint.getName());

        Date startDate = new Date();
        sprint.setStartDate(startDate);
        assertEquals(startDate, sprint.getStartDate());

        Date endDate = new Date();
        sprint.setEndDate(endDate);
        assertEquals(endDate, sprint.getEndDate());

        sprint.setTasks(List.of());
        assertNotNull(sprint.getTasks());
        assertTrue(sprint.getTasks().isEmpty());
    }

    @Test
    void testToString() {
        Sprint sprint = new Sprint();
        sprint.setId(1L);
        sprint.setName("Sprint Name");
        sprint.setStartDate(new Date());
        sprint.setEndDate(new Date());

        String toStringResult = sprint.toString();
        assertTrue(toStringResult.contains("id=1"));
        assertTrue(toStringResult.contains("name='Sprint Name'"));
    }

    @Test
    void testValidation() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        // Create a valid Sprint object
        Sprint sprint = new Sprint(1L, "Valid Sprint", new Date(), new Date(), List.of());

        // Validate the Sprint object
        Set<ConstraintViolation<Sprint>> violations = validator.validate(sprint);

        // Assert no violations
        assertTrue(violations.isEmpty());
    }

    @Test
    void testInvalidSprint() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        // Create an invalid Sprint object
        Sprint sprint = new Sprint();
        sprint.setName(""); // Name should not be blank

        // Validate the Sprint object
        Set<ConstraintViolation<Sprint>> violations = validator.validate(sprint);

        // Assert violations are found
        assertFalse(violations.isEmpty());

        // Print violations for debugging
        violations.forEach(v -> System.out.println(v.getPropertyPath() + ": " + v.getMessage()));
    }
}
