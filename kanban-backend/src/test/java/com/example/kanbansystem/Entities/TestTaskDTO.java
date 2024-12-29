package com.example.kanbansystem.Entities;

import com.example.kanbansystem.dto.TaskDTO;
import com.example.kanbansystem.entities.TaskStatus;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TaskDTOTest {

    @Test
    void testDefaultConstructor() {
        TaskDTO taskDTO = new TaskDTO();

        assertNull(taskDTO.getId());
        assertNull(taskDTO.getName());
        assertNull(taskDTO.getDescription());
        assertNull(taskDTO.getStatus());
        assertNull(taskDTO.getEstimation());
        assertNull(taskDTO.getBoardId());
        assertNull(taskDTO.getUserIds());
        assertNull(taskDTO.getSprintIds());
    }

    @Test
    void testParameterizedConstructor() {
        List<Long> userIds = List.of(1L, 2L);
        List<Long> sprintIds = List.of(3L, 4L);

        TaskDTO taskDTO = new TaskDTO(
                1L, 
                "Task Name", 
                "Task Description", 
                TaskStatus.TODO, 
                5, 
                100L, 
                userIds, 
                sprintIds
        );

        assertEquals(1L, taskDTO.getId());
        assertEquals("Task Name", taskDTO.getName());
        assertEquals("Task Description", taskDTO.getDescription());
        assertEquals(TaskStatus.TODO, taskDTO.getStatus());
        assertEquals(5, taskDTO.getEstimation());
        assertEquals(100L, taskDTO.getBoardId());
        assertEquals(userIds, taskDTO.getUserIds());
        assertEquals(sprintIds, taskDTO.getSprintIds());
    }

    @Test
    void testSettersAndGetters() {
        TaskDTO taskDTO = new TaskDTO();

        taskDTO.setId(10L);
        assertEquals(10L, taskDTO.getId());

        taskDTO.setName("Updated Task Name");
        assertEquals("Updated Task Name", taskDTO.getName());

        taskDTO.setDescription("Updated Task Description");
        assertEquals("Updated Task Description", taskDTO.getDescription());

        taskDTO.setStatus(TaskStatus.IN_PROGRESS);
        assertEquals(TaskStatus.IN_PROGRESS, taskDTO.getStatus());

        taskDTO.setEstimation(8);
        assertEquals(8, taskDTO.getEstimation());

        taskDTO.setBoardId(200L);
        assertEquals(200L, taskDTO.getBoardId());

        List<Long> userIds = List.of(3L, 4L);
        taskDTO.setUserIds(userIds);
        assertEquals(userIds, taskDTO.getUserIds());

        List<Long> sprintIds = List.of(5L, 6L);
        taskDTO.setSprintIds(sprintIds);
        assertEquals(sprintIds, taskDTO.getSprintIds());
    }

    @Test
    void testToString() {
        List<Long> userIds = List.of(1L, 2L);
        List<Long> sprintIds = List.of(3L, 4L);

        TaskDTO taskDTO = new TaskDTO(
                1L, 
                "Task Name", 
                "Task Description", 
                TaskStatus.DONE, 
                7, 
                300L, 
                userIds, 
                sprintIds
        );

        String expected = "TaskDTO{id=1, name='Task Name', description='Task Description', status=DONE, " +
                "estimation=7, boardId=300, userIds=[1, 2], sprintIds=[3, 4]}";
        assertEquals(expected, taskDTO.toString());
    }

    @Test
    void testNullValues() {
        TaskDTO taskDTO = new TaskDTO();
        taskDTO.setName(null);
        taskDTO.setDescription(null);
        taskDTO.setStatus(null);

        assertNull(taskDTO.getName());
        assertNull(taskDTO.getDescription());
        assertNull(taskDTO.getStatus());
    }

    @Test
    void testEmptyUserAndSprintIds() {
        TaskDTO taskDTO = new TaskDTO();

        taskDTO.setUserIds(List.of());
        taskDTO.setSprintIds(List.of());

        assertTrue(taskDTO.getUserIds().isEmpty());
        assertTrue(taskDTO.getSprintIds().isEmpty());
    }
}
