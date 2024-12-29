package com.example.kanbansystem.entities;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.BatchSize;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Name is mandatory")
    private String name;

    @NotBlank(message = "Description is mandatory")
    private String description;

    @NotNull(message = "Task status is mandatory")
    @Enumerated(EnumType.STRING)
    private TaskStatus status;

    private Integer estimation;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private Board board;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "task_users",
        joinColumns = @JoinColumn(name = "task_id"),
        inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    @BatchSize(size = 10) // Fetch users in batches of 10
    @JsonIgnoreProperties("tasks") // Prevent circular references
    private List<User> users;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "task_sprints",
        joinColumns = @JoinColumn(name = "task_id"),
        inverseJoinColumns = @JoinColumn(name = "sprint_id")
    )
    @BatchSize(size = 10) // Fetch sprints in batches of 10
    @JsonIgnoreProperties("tasks") // Prevent circular references
    private List<Sprint> sprints;

    // Constructors, Getters, and Setters
    public Task() {}

    public Task(Long id, String name, String description, TaskStatus status, Integer estimation, Board board, List<User> users, List<Sprint> sprints) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.status = status;
        this.estimation = estimation;
        this.board = board;
        this.users = users;
        this.sprints = sprints;
    }

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

    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public List<Sprint> getSprints() {
        return sprints;
    }

    public void setSprints(List<Sprint> sprints) {
        this.sprints = sprints;
    }
}
