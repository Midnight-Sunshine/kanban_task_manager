package com.example.kanbansystem.Service;

import com.example.kanbansystem.Repository.BoardRepository;
import com.example.kanbansystem.Repository.SprintRepository;
import com.example.kanbansystem.Repository.TaskRepository;
import com.example.kanbansystem.Repository.UserRepository;
import com.example.kanbansystem.dto.TaskDTO;
import com.example.kanbansystem.entities.*;
import com.example.kanbansystem.service.TaskService;

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
public class TestTaskService {

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private SprintRepository sprintRepository;

    @Mock
    private BoardRepository boardRepository;

    @InjectMocks
    private TaskService taskService;

    @Test
    public void testGetTaskByName() {
        String taskName = "Task 1";
        List<Task> mockTasks = Arrays.asList(new Task(), new Task());
        when(taskRepository.findTaskByName(taskName)).thenReturn(mockTasks);

        List<Task> result = taskService.getTaskByName(taskName);

        assertEquals(mockTasks.size(), result.size());
        assertEquals(mockTasks, result);
        verify(taskRepository, times(1)).findTaskByName(taskName);
    }

    @Test
    public void testGetAllTask() {
        List<Task> mockTasks = Arrays.asList(new Task(), new Task());
        when(taskRepository.findAll()).thenReturn(mockTasks);

        List<Task> result = taskService.getAllTask();

        assertEquals(mockTasks.size(), result.size());
        assertEquals(mockTasks, result);
        verify(taskRepository, times(1)).findAll();
    }

    @Test
    public void testGetTaskById() {
        Long taskId = 1L;
        Task mockTask = new Task();
        when(taskRepository.findById(taskId)).thenReturn(Optional.of(mockTask));

        Optional<Task> result = taskService.getTaskById(taskId);

        assertTrue(result.isPresent());
        assertEquals(mockTask, result.get());
        verify(taskRepository, times(1)).findById(taskId);
    }

    @Test
    public void testGetTasksByBoardId() {
        Long boardId = 1L;
        List<Task> mockTasks = Arrays.asList(new Task(), new Task());
        when(taskRepository.findByBoardId(boardId)).thenReturn(mockTasks);

        List<Task> result = taskService.getTasksByBoardId(boardId);

        assertEquals(mockTasks.size(), result.size());
        assertEquals(mockTasks, result);
        verify(taskRepository, times(1)).findByBoardId(boardId);
    }

    @Test
    public void testSaveTask() {
        Task mockTask = new Task();
        when(taskRepository.save(any(Task.class))).thenReturn(mockTask);

        Task result = taskService.saveTask(mockTask);

        assertEquals(mockTask, result);
        verify(taskRepository, times(1)).save(mockTask);
    }

    @Test
    public void testDeleteTaskById() {
        Long taskId = 1L;

        String result = taskService.deleteTaskById(taskId);

        assertEquals("Task with ID " + taskId + " deleted successfully!", result);
        verify(taskRepository, times(1)).deleteById(taskId);
    }

    @Test
    public void testUpdateTaskStatus() {
        Long taskId = 1L;
        TaskStatus status = TaskStatus.IN_PROGRESS;
        Task mockTask = new Task();
        when(taskRepository.findById(taskId)).thenReturn(Optional.of(mockTask));

        taskService.updateTaskStatus(taskId, status);

        assertEquals(status, mockTask.getStatus());
        verify(taskRepository, times(1)).save(mockTask);
    }

    @Test
    public void testGetUsersByIds() {
        List<Long> userIds = Arrays.asList(1L, 2L);
        List<User> mockUsers = Arrays.asList(new User(), new User());
        when(userRepository.findAllById(userIds)).thenReturn(mockUsers);

        List<User> result = taskService.getUsersByIds(userIds);

        assertEquals(mockUsers.size(), result.size());
        assertEquals(mockUsers, result);
        verify(userRepository, times(1)).findAllById(userIds);
    }

    @Test
    public void testGetSprintsByIds() {
        List<Long> sprintIds = Arrays.asList(1L, 2L);
        List<Sprint> mockSprints = Arrays.asList(new Sprint(), new Sprint());
        when(sprintRepository.findAllById(sprintIds)).thenReturn(mockSprints);

        List<Sprint> result = taskService.getSprintsByIds(sprintIds);

        assertEquals(mockSprints.size(), result.size());
        assertEquals(mockSprints, result);
        verify(sprintRepository, times(1)).findAllById(sprintIds);
    }

    @Test
    public void testCreateTaskFromDTO() {
        TaskDTO mockDTO = new TaskDTO();
        mockDTO.setBoardId(1L);
        mockDTO.setName("Test Task");
        mockDTO.setDescription("Description");
        mockDTO.setStatus(TaskStatus.TODO);
        mockDTO.setEstimation(5);
        mockDTO.setUserIds(Arrays.asList(1L, 2L));
        mockDTO.setSprintIds(Arrays.asList(3L, 4L));

        Board mockBoard = new Board();
        when(boardRepository.findById(1L)).thenReturn(Optional.of(mockBoard));
        when(userRepository.findAllById(mockDTO.getUserIds())).thenReturn(Arrays.asList(new User(), new User()));
        when(sprintRepository.findAllById(mockDTO.getSprintIds())).thenReturn(Arrays.asList(new Sprint(), new Sprint()));
        Task mockTask = new Task();
        when(taskRepository.save(any(Task.class))).thenReturn(mockTask);

        Task result = taskService.createTaskFromDTO(mockDTO);

        assertEquals(mockTask, result);
        verify(taskRepository, times(1)).save(any(Task.class));
    }

    @Test
    public void testUpdateTaskFromDTO() {
        // Mock input DTO
        TaskDTO mockDTO = new TaskDTO();
        mockDTO.setId(1L);
        mockDTO.setBoardId(1L);
        mockDTO.setName("Updated Task");
        mockDTO.setDescription("Updated Description");
        mockDTO.setStatus(TaskStatus.IN_PROGRESS);
        mockDTO.setEstimation(8);
        mockDTO.setUserIds(Arrays.asList(1L, 2L));
        mockDTO.setSprintIds(Arrays.asList(3L, 4L));

        // Mock existing entities
        Task mockTask = new Task();
        mockTask.setId(1L);

        Board mockBoard = new Board();
        mockBoard.setId(1L);

        List<User> mockUsers = Arrays.asList(new User(), new User());
        List<Sprint> mockSprints = Arrays.asList(new Sprint(), new Sprint());

        // Set up mocks
        when(taskRepository.findById(1L)).thenReturn(Optional.of(mockTask));
        when(boardRepository.findById(1L)).thenReturn(Optional.of(mockBoard));
        when(userRepository.findAllById(mockDTO.getUserIds())).thenReturn(mockUsers);
        when(sprintRepository.findAllById(mockDTO.getSprintIds())).thenReturn(mockSprints);
        when(taskRepository.save(any(Task.class))).thenReturn(mockTask);

        // Call the method under test
        Task result = taskService.updateTaskFromDTO(mockDTO);

        // Verify behavior and assert results
        assertNotNull(result);
        assertEquals(mockTask, result);
        assertEquals("Updated Task", result.getName());
        assertEquals("Updated Description", result.getDescription());
        assertEquals(TaskStatus.IN_PROGRESS, result.getStatus());
        assertEquals(8, result.getEstimation());
        assertEquals(mockBoard, result.getBoard());
        assertEquals(mockUsers, result.getUsers());
        assertEquals(mockSprints, result.getSprints());

        verify(taskRepository, times(1)).findById(1L);
        verify(boardRepository, times(1)).findById(1L);
        verify(userRepository, times(1)).findAllById(mockDTO.getUserIds());
        verify(sprintRepository, times(1)).findAllById(mockDTO.getSprintIds());
        verify(taskRepository, times(1)).save(any(Task.class));
    }

}
