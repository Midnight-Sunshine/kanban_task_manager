package com.example.kanbansystem.controller;

import com.example.kanbansystem.dto.TaskDTO;
import com.example.kanbansystem.entities.Board;
import com.example.kanbansystem.entities.Sprint;
import com.example.kanbansystem.entities.Task;
import com.example.kanbansystem.entities.TaskStatus;
import com.example.kanbansystem.entities.User;
import com.example.kanbansystem.service.BoardService;
import com.example.kanbansystem.service.EmailService;
import com.example.kanbansystem.service.SprintService;
import com.example.kanbansystem.service.TaskService;
import com.example.kanbansystem.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/")
@CrossOrigin(origins = "*")
public class TaskController {
    @Autowired
    private TaskService taskService;
    @Autowired
    private EmailService emailService;
    @Autowired
    private BoardService boardService;
    @Autowired
    private UserService userService;
    @Autowired
    private SprintService sprintService;
    @GetMapping("/tasks/{taskName}")
    public ResponseEntity<List<Task>> getTaskByName(@PathVariable("taskName") String taskName) {
        List<Task> task = taskService.getTaskByName(taskName);
        if (task != null) {
            return new ResponseEntity<>(task, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping("/tasksId/{taskId}")
    public ResponseEntity<Task> getTaskById(@PathVariable("taskId") Long taskId) {
        Optional<Task> task = taskService.getTaskById(taskId);
        if (task.isPresent()) {
            return new ResponseEntity<>(task.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping("/tasks")
    public ResponseEntity<List<Task>> getAllTask() {
        List<Task> tasks = taskService.getAllTask();
        return new ResponseEntity<>(tasks, HttpStatus.OK);
    }
    @PostMapping("/tasks")
    public ResponseEntity<Task> saveTask(@RequestBody TaskDTO taskDTO) {
        // Fetch Board
        Optional<Board> boardOptional = boardService.getBoardById(taskDTO.getBoardId());
        if (!boardOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        Board board = boardOptional.get();

        // Fetch Users
        List<User> users = userService.getUsersByIds(taskDTO.getUserIds());

        // Fetch Sprints
        List<Sprint> sprints = sprintService.getSprintsByIds(taskDTO.getSprintIds());

        // Create Task
        Task task = new Task();
        task.setName(taskDTO.getName());
        task.setDescription(taskDTO.getDescription());
        task.setStatus(taskDTO.getStatus());
        task.setEstimation(taskDTO.getEstimation());
        task.setBoard(board);
        task.setUsers(users);
        task.setSprints(sprints);

        // Save Task
        Task savedTask = taskService.saveTask(task);
        return new ResponseEntity<>(savedTask, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/tasksDeleted/{taskId}", method = {RequestMethod.DELETE, RequestMethod.GET})
    public ResponseEntity<String> deleteTaskById(@PathVariable("taskId") Long taskId) {
        String result = taskService.deleteTaskById(taskId);
        if (result != null) {
            return new ResponseEntity<>(result, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping("/tasks/board/{boardId}")
    public ResponseEntity<List<Task>> getTasksByBoardId(@PathVariable("boardId") Long boardId) {
        List<Task> tasks = taskService.getTasksByBoardId(boardId);
        if (tasks != null && !tasks.isEmpty()) {
            return new ResponseEntity<>(tasks, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @PutMapping("/tasks/{taskId}/status")
    public ResponseEntity<?> updateTaskStatus(@PathVariable Long taskId, @RequestParam TaskStatus status) {
        try {
            taskService.updateTaskStatus(taskId, status);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    @PutMapping("/tasks")
    public ResponseEntity<Task> updateTask(@RequestBody TaskDTO taskDTO) {
        try {
            Task updatedTask = taskService.updateTaskFromDTO(taskDTO);
            return new ResponseEntity<>(updatedTask, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

}
