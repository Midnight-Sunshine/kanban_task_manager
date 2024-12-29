package com.example.kanbansystem.Repository;


import com.example.kanbansystem.entities.Board;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<Board,Long> {
    Board findBoardByName(String name);


}
