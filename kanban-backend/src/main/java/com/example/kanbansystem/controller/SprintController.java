package com.example.kanbansystem.controller;
import com.example.kanbansystem.entities.Sprint;
import com.example.kanbansystem.service.SprintService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class SprintController {
    @Autowired
    private SprintService sprintService;
    @GetMapping("/sprints/{sprintName}")
    public ResponseEntity<List<Sprint>> getTaskByName(@PathVariable("sprintName") String sprintName) {
        List<Sprint> sprint = sprintService.getSprintByName(sprintName);
        if (sprint != null) {
            return new ResponseEntity<>(sprint, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping("/sprintId/{sprintId}")
    public ResponseEntity<Sprint> getsprintById(@PathVariable("sprintId") Long sprintId) {
        Optional<Sprint> sprint = sprintService.getSprintById(sprintId);
        if (sprint.isPresent()) {
            return new ResponseEntity<>(sprint.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping("/sprints")
    public ResponseEntity<List<Sprint>> getAllSprint() {
        List<Sprint> sprint = sprintService.getAllSprint();
        return new ResponseEntity<>(sprint, HttpStatus.OK);
    }
    @PostMapping("/sprints")
    public ResponseEntity<Sprint> saveSprint(@RequestBody Sprint sprint) {
        Sprint savedSprint = sprintService.saveSprint(sprint);
        return new ResponseEntity<>(savedSprint, HttpStatus.CREATED);
    }
    @RequestMapping(value = "/sprintDeleted/{sprintId}", method = {RequestMethod.DELETE, RequestMethod.GET})
    public ResponseEntity<String> deleteSprintById(@PathVariable("sprintId") Long sprintId) {
        String result = sprintService.deleteSprintById(sprintId);
        if (result != null) {
            return new ResponseEntity<>(result, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
