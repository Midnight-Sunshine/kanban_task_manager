package com.example.kanbansystem.service;

import com.example.kanbansystem.Repository.TaskRepository;
import com.example.kanbansystem.Repository.UserRepository;
import com.example.kanbansystem.dto.TaskDTO;
import com.example.kanbansystem.Repository.SprintRepository;
import com.example.kanbansystem.Repository.BoardRepository;
import com.example.kanbansystem.entities.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SprintRepository sprintRepository;

    @Autowired
    private BoardRepository boardRepository;

    /**
     * Fetch tasks by name
     */
    public List<Task> getTaskByName(String name) {
        return taskRepository.findTaskByName(name);
    }

    /**
     * Fetch all tasks
     */
    public List<Task> getAllTask() {
        return taskRepository.findAll();
    }

    /**
     * Fetch task by ID
     */
    public Optional<Task> getTaskById(Long id) {
        return taskRepository.findById(id);
    }

    /**
     * Fetch tasks by board ID
     */
    public List<Task> getTasksByBoardId(Long boardId) {
        return taskRepository.findByBoardId(boardId);
    }

    /**
     * Save a task with associated relationships
     */
    public Task saveTask(Task task) {
        return taskRepository.save(task);
    }

    /**
     * Delete a task by ID
     */
    public String deleteTaskById(Long id) {
        taskRepository.deleteById(id);
        return "Task with ID " + id + " deleted successfully!";
    }

    /**
     * Update the status of a task
     */
    public void updateTaskStatus(Long taskId, TaskStatus status) {
        Optional<Task> optionalTask = taskRepository.findById(taskId);
        if (optionalTask.isPresent()) {
            Task task = optionalTask.get();
            task.setStatus(status);
            taskRepository.save(task);
        } else {
            throw new RuntimeException("Task not found with ID " + taskId);
        }
    }

    /**
     * Associate users with a task
     */
    public List<User> getUsersByIds(List<Long> userIds) {
        return userRepository.findAllById(userIds);
    }

    /**
     * Associate sprints with a task
     */
    public List<Sprint> getSprintsByIds(List<Long> sprintIds) {
        return sprintRepository.findAllById(sprintIds);
    }

    /**
     * Fetch board by ID
     */
    public Optional<Board> getBoardById(Long boardId) {
        return boardRepository.findById(boardId);
    }

    /**
     * Create a task from DTO
     */
    public Task createTaskFromDTO(TaskDTO taskDTO) {
        // Fetch and validate the board
        Board board = getBoardById(taskDTO.getBoardId())
                .orElseThrow(() -> new RuntimeException("Board not found with ID " + taskDTO.getBoardId()));

        // Fetch users and sprints
        List<User> users = getUsersByIds(taskDTO.getUserIds());
        List<Sprint> sprints = getSprintsByIds(taskDTO.getSprintIds());

        // Create and populate task
        Task task = new Task();
        task.setName(taskDTO.getName());
        task.setDescription(taskDTO.getDescription());
        task.setStatus(taskDTO.getStatus());
        task.setEstimation(taskDTO.getEstimation());
        task.setBoard(board);
        task.setUsers(users);
        task.setSprints(sprints);

        return saveTask(task);
    }

    /**
     * Update a task from DTO
     */
    public Task updateTaskFromDTO(TaskDTO taskDTO) {
        // Fetch and validate the existing task
        Task existingTask = taskRepository.findById(taskDTO.getId())
                .orElseThrow(() -> new RuntimeException("Task not found with ID " + taskDTO.getId()));

        // Fetch and validate the board
        Board board = getBoardById(taskDTO.getBoardId())
                .orElseThrow(() -> new RuntimeException("Board not found with ID " + taskDTO.getBoardId()));

        // Fetch users and sprints
        List<User> users = getUsersByIds(taskDTO.getUserIds());
        List<Sprint> sprints = getSprintsByIds(taskDTO.getSprintIds());

        // Update task details
        existingTask.setName(taskDTO.getName());
        existingTask.setDescription(taskDTO.getDescription());
        existingTask.setStatus(taskDTO.getStatus());
        existingTask.setEstimation(taskDTO.getEstimation());
        existingTask.setBoard(board);
        existingTask.setUsers(users);
        existingTask.setSprints(sprints);

        // Save updated task
        return taskRepository.save(existingTask);
    }
}
