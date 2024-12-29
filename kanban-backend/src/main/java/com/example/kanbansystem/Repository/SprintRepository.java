package com.example.kanbansystem.Repository;

import com.example.kanbansystem.entities.Sprint;
import com.example.kanbansystem.entities.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SprintRepository extends JpaRepository<Sprint,Long> {
    List<Sprint> findSprintByName(String name);

}
