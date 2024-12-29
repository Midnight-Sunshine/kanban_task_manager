package com.example.kanbansystem.Service;

import com.example.kanbansystem.Repository.SprintRepository;
import com.example.kanbansystem.entities.Sprint;
import com.example.kanbansystem.service.SprintService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TestSprintService {

    @Mock
    private SprintRepository sprintRepository;

    @InjectMocks
    private SprintService sprintService;

    @Test
    public void testSaveSprint() {
        Sprint mockSprint = new Sprint();
        mockSprint.setName("Sprint 1");

        when(sprintRepository.save(any(Sprint.class))).thenReturn(mockSprint);

        Sprint result = sprintService.saveSprint(mockSprint);

        assertNotNull(result);
        assertEquals("Sprint 1", result.getName());
        verify(sprintRepository, times(1)).save(mockSprint);
    }

    @Test
    public void testGetSprintById() {
        Sprint mockSprint = new Sprint();
        mockSprint.setId(1L);

        when(sprintRepository.findById(1L)).thenReturn(Optional.of(mockSprint));

        Optional<Sprint> result = sprintService.getSprintById(1L);

        assertTrue(result.isPresent());
        assertEquals(1L, result.get().getId());
        verify(sprintRepository, times(1)).findById(1L);
    }

    @Test
    public void testGetAllSprint() {
        Sprint sprint1 = new Sprint();
        Sprint sprint2 = new Sprint();
        List<Sprint> mockSprints = Arrays.asList(sprint1, sprint2);

        when(sprintRepository.findAll()).thenReturn(mockSprints);

        List<Sprint> result = sprintService.getAllSprint();

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(sprintRepository, times(1)).findAll();
    }

    @Test
    public void testGetSprintByName() {
        Sprint sprint1 = new Sprint();
        sprint1.setName("Sprint 1");
        List<Sprint> mockSprints = Arrays.asList(sprint1);

        when(sprintRepository.findSprintByName("Sprint 1")).thenReturn(mockSprints);

        List<Sprint> result = sprintService.getSprintByName("Sprint 1");

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Sprint 1", result.get(0).getName());
        verify(sprintRepository, times(1)).findSprintByName("Sprint 1");
    }

    @Test
    public void testDeleteSprintById() {
        Long sprintId = 1L;

        doNothing().when(sprintRepository).deleteById(sprintId);

        String result = sprintService.deleteSprintById(sprintId);

        assertEquals("Sprint with ID 1 deleted successfully!", result);
        verify(sprintRepository, times(1)).deleteById(sprintId);
    }

    @Test
    public void testGetSprintsByIds() {
        Sprint sprint1 = new Sprint();
        sprint1.setId(1L);
        Sprint sprint2 = new Sprint();
        sprint2.setId(2L);
        List<Sprint> mockSprints = Arrays.asList(sprint1, sprint2);

        when(sprintRepository.findAllById(Arrays.asList(1L, 2L))).thenReturn(mockSprints);

        List<Sprint> result = sprintService.getSprintsByIds(Arrays.asList(1L, 2L));

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(1L, result.get(0).getId());
        assertEquals(2L, result.get(1).getId());
        verify(sprintRepository, times(1)).findAllById(Arrays.asList(1L, 2L));
    }
}
