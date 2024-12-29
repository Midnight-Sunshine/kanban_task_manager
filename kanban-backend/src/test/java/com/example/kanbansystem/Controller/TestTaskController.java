package com.example.kanbansystem.Controller;
import com.example.kanbansystem.controller.TaskController;
import com.example.kanbansystem.dto.TaskDTO;
import com.example.kanbansystem.entities.Board;
import com.example.kanbansystem.entities.Task;
import com.example.kanbansystem.entities.TaskStatus;
import com.example.kanbansystem.service.BoardService;
import com.example.kanbansystem.service.EmailService;
import com.example.kanbansystem.service.SprintService;
import com.example.kanbansystem.service.TaskService;
import com.example.kanbansystem.service.UserService;

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
public class TestTaskController {

    @Mock
    private TaskService taskService;

    @Mock
    private EmailService emailService;

    @Mock
    private BoardService boardService;

    @Mock
    private UserService userService;

    @Mock
    private SprintService sprintService;

    @InjectMocks
    private TaskController taskController;

    @Test
    public void testGetTaskByName_Found() {
        String taskName = "task 1";
        List<Task> mockTasks = new ArrayList<>();
        mockTasks.add(new Task());
        when(taskService.getTaskByName(taskName)).thenReturn(mockTasks);

        ResponseEntity<List<Task>> responseEntity = taskController.getTaskByName(taskName);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(mockTasks, responseEntity.getBody());
    }

    @Test
    public void testGetTaskByName_NotFound() {
        String taskName = "nonexistent task";
        when(taskService.getTaskByName(taskName)).thenReturn(null);

        ResponseEntity<List<Task>> responseEntity = taskController.getTaskByName(taskName);

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }

    @Test
    public void testGetTaskById_Found() {
        Long taskId = 1L;
        Task mockTask = new Task();
        when(taskService.getTaskById(taskId)).thenReturn(Optional.of(mockTask));

        ResponseEntity<Task> responseEntity = taskController.getTaskById(taskId);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(mockTask, responseEntity.getBody());
    }

    @Test
    public void testGetTaskById_NotFound() {
        Long taskId = 1L;
        when(taskService.getTaskById(taskId)).thenReturn(Optional.empty());

        ResponseEntity<Task> responseEntity = taskController.getTaskById(taskId);

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }

    @Test
    public void testGetAllTask() {
        List<Task> mockTasks = new ArrayList<>();
        when(taskService.getAllTask()).thenReturn(mockTasks);

        ResponseEntity<List<Task>> responseEntity = taskController.getAllTask();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(mockTasks, responseEntity.getBody());
    }

    @Test
    public void testSaveTask_Success() {
        TaskDTO mockTaskDTO = new TaskDTO();
        mockTaskDTO.setBoardId(1L);
        mockTaskDTO.setName("Test Task");

        Board mockBoard = new Board();
        Task mockTask = new Task();

        when(boardService.getBoardById(mockTaskDTO.getBoardId())).thenReturn(Optional.of(mockBoard));
        when(userService.getUsersByIds(mockTaskDTO.getUserIds())).thenReturn(new ArrayList<>());
        when(sprintService.getSprintsByIds(mockTaskDTO.getSprintIds())).thenReturn(new ArrayList<>());
        when(taskService.saveTask(any(Task.class))).thenReturn(mockTask);

        ResponseEntity<Task> responseEntity = taskController.saveTask(mockTaskDTO);

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals(mockTask, responseEntity.getBody());
    }

    @Test
    public void testSaveTask_InvalidBoard() {
        TaskDTO mockTaskDTO = new TaskDTO();
        mockTaskDTO.setBoardId(1L);

        when(boardService.getBoardById(mockTaskDTO.getBoardId())).thenReturn(Optional.empty());

        ResponseEntity<Task> responseEntity = taskController.saveTask(mockTaskDTO);

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    }

    @Test
    public void testDeleteTaskById_Success() {
        Long taskId = 1L;
        String expectedResult = "Deleted";

        when(taskService.deleteTaskById(taskId)).thenReturn(expectedResult);

        ResponseEntity<String> responseEntity = taskController.deleteTaskById(taskId);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(expectedResult, responseEntity.getBody());
    }

    @Test
    public void testDeleteTaskById_NotFound() {
        Long taskId = 1L;

        when(taskService.deleteTaskById(taskId)).thenReturn(null);

        ResponseEntity<String> responseEntity = taskController.deleteTaskById(taskId);

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }

    @Test
    public void testUpdateTaskStatus_Success() {
        Long taskId = 1L;
        TaskStatus status = TaskStatus.IN_PROGRESS;

        doNothing().when(taskService).updateTaskStatus(taskId, status);

        ResponseEntity<?> responseEntity = taskController.updateTaskStatus(taskId, status);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    public void testUpdateTaskStatus_NotFound() {
        Long taskId = 1L;
        TaskStatus status = TaskStatus.IN_PROGRESS;

        doThrow(new RuntimeException("Task not found")).when(taskService).updateTaskStatus(taskId, status);

        ResponseEntity<?> responseEntity = taskController.updateTaskStatus(taskId, status);

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }

    @Test
    public void testUpdateTask_Success() {
        TaskDTO mockTaskDTO = new TaskDTO();
        Task mockTask = new Task();

        when(taskService.updateTaskFromDTO(mockTaskDTO)).thenReturn(mockTask);

        ResponseEntity<Task> responseEntity = taskController.updateTask(mockTaskDTO);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(mockTask, responseEntity.getBody());
    }

    @Test
    public void testUpdateTask_Failure() {
        TaskDTO mockTaskDTO = new TaskDTO();

        when(taskService.updateTaskFromDTO(mockTaskDTO)).thenThrow(new RuntimeException("Update failed"));

        ResponseEntity<Task> responseEntity = taskController.updateTask(mockTaskDTO);

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    }
}
