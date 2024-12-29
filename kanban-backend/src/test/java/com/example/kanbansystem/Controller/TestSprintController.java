package com.example.kanbansystem.Controller;
import com.example.kanbansystem.controller.SprintController;
import com.example.kanbansystem.entities.Sprint;
import com.example.kanbansystem.service.SprintService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TestSprintController {

    @Mock
    private SprintService sprintService;

    @InjectMocks
    private SprintController sprintController;

    @Test
    public void testGetSprintByName_Found() {
        String sprintName = "Sprint 1";
        List<Sprint> mockSprints = new ArrayList<>();
        mockSprints.add(new Sprint());
        when(sprintService.getSprintByName(sprintName)).thenReturn(mockSprints);

        ResponseEntity<List<Sprint>> responseEntity = sprintController.getTaskByName(sprintName);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(mockSprints, responseEntity.getBody());
    }

    @Test
    public void testGetSprintByName_NotFound() {
        String sprintName = "Nonexistent Sprint";
        when(sprintService.getSprintByName(sprintName)).thenReturn(null);

        ResponseEntity<List<Sprint>> responseEntity = sprintController.getTaskByName(sprintName);

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }

    @Test
    public void testGetSprintById_Found() {
        Long sprintId = 1L;
        Sprint mockSprint = new Sprint();
        when(sprintService.getSprintById(sprintId)).thenReturn(Optional.of(mockSprint));

        ResponseEntity<Sprint> responseEntity = sprintController.getsprintById(sprintId);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(mockSprint, responseEntity.getBody());
    }

    @Test
    public void testGetSprintById_NotFound() {
        Long sprintId = 1L;
        when(sprintService.getSprintById(sprintId)).thenReturn(Optional.empty());

        ResponseEntity<Sprint> responseEntity = sprintController.getsprintById(sprintId);

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }

    @Test
    public void testGetAllSprint() {
        List<Sprint> mockSprints = new ArrayList<>();
        mockSprints.add(new Sprint());
        when(sprintService.getAllSprint()).thenReturn(mockSprints);

        ResponseEntity<List<Sprint>> responseEntity = sprintController.getAllSprint();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(mockSprints, responseEntity.getBody());
    }

    @Test
    public void testSaveSprint_Valid() {
        Sprint mockSprint = new Sprint();
        when(sprintService.saveSprint(any(Sprint.class))).thenReturn(mockSprint);

        ResponseEntity<Sprint> responseEntity = sprintController.saveSprint(mockSprint);

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals(mockSprint, responseEntity.getBody());
    }

    @Test
    public void testSaveSprint_Invalid() {
        Sprint mockSprint = null; // Invalid sprint
        when(sprintService.saveSprint(mockSprint)).thenThrow(new IllegalArgumentException("Invalid sprint"));

        try {
            sprintController.saveSprint(mockSprint);
        } catch (IllegalArgumentException e) {
            assertEquals("Invalid sprint", e.getMessage());
        }
    }

    @Test
    public void testDeleteSprintById_Success() {
        Long sprintId = 1L;
        String expectedResult = "Deleted";
        when(sprintService.deleteSprintById(sprintId)).thenReturn(expectedResult);

        ResponseEntity<String> responseEntity = sprintController.deleteSprintById(sprintId);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(expectedResult, responseEntity.getBody());
    }

    @Test
    public void testDeleteSprintById_NotFound() {
        Long sprintId = 1L;
        when(sprintService.deleteSprintById(sprintId)).thenReturn(null);

        ResponseEntity<String> responseEntity = sprintController.deleteSprintById(sprintId);

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }

    @Test
    public void testDeleteSprintById_Failure() {
        Long sprintId = 1L;
        when(sprintService.deleteSprintById(sprintId)).thenThrow(new RuntimeException("Delete operation failed"));

        try {
            sprintController.deleteSprintById(sprintId);
        } catch (RuntimeException e) {
            assertEquals("Delete operation failed", e.getMessage());
        }
    }
}
