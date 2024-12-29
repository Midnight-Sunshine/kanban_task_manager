package com.example.kanbansystem.dto;

import com.example.kanbansystem.entities.TaskStatus;

import java.util.List;

public class TaskDTO {

    private Long id; // Task ID for updates
    private String name;
    private String description;
    private TaskStatus status;
    private Integer estimation;
    private Long boardId; // ID of the associated board
    private List<Long> userIds; // List of user IDs to associate with the task
    private List<Long> sprintIds; // List of sprint IDs to associate with the task

    // Default Constructor
    public TaskDTO() {}

    // Parameterized Constructor
    public TaskDTO(Long id, String name, String description, TaskStatus status, Integer estimation, Long boardId, List<Long> userIds, List<Long> sprintIds) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.status = status;
        this.estimation = estimation;
        this.boardId = boardId;
        this.userIds = userIds;
        this.sprintIds = sprintIds;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }

    public Integer getEstimation() {
        return estimation;
    }

    public void setEstimation(Integer estimation) {
        this.estimation = estimation;
    }

    public Long getBoardId() {
        return boardId;
    }

    public void setBoardId(Long boardId) {
        this.boardId = boardId;
    }

    public List<Long> getUserIds() {
        return userIds;
    }

    public void setUserIds(List<Long> userIds) {
        this.userIds = userIds;
    }

    public List<Long> getSprintIds() {
        return sprintIds;
    }

    public void setSprintIds(List<Long> sprintIds) {
        this.sprintIds = sprintIds;
    }

    @Override
    public String toString() {
        return "TaskDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", status=" + status +
                ", estimation=" + estimation +
                ", boardId=" + boardId +
                ", userIds=" + userIds +
                ", sprintIds=" + sprintIds +
                '}';
    }
}
