package com.example.kanbansystem.service;

import com.example.kanbansystem.Repository.SprintRepository;
import com.example.kanbansystem.entities.Sprint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SprintService {

    @Autowired
    private SprintRepository sprintRepository;

    /**
     * Save a new sprint
     */
    public Sprint saveSprint(Sprint sprint) {
        return sprintRepository.save(sprint);
    }

    /**
     * Fetch a sprint by ID
     */
    public Optional<Sprint> getSprintById(long id) {
        return sprintRepository.findById(id);
    }

    /**
     * Fetch all sprints
     */
    public List<Sprint> getAllSprint() {
        return sprintRepository.findAll();
    }

    /**
     * Fetch sprints by name
     */
    public List<Sprint> getSprintByName(String name) {
        return sprintRepository.findSprintByName(name);
    }

    /**
     * Delete a sprint by ID
     */
    public String deleteSprintById(Long id) {
        sprintRepository.deleteById(id);
        return "Sprint with ID " + id + " deleted successfully!";
    }

    /**
     * Fetch sprints by a list of IDs
     */
    public List<Sprint> getSprintsByIds(List<Long> sprintIds) {
        return sprintRepository.findAllById(sprintIds);
    }
}
