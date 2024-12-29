package com.example.kanbansystem.service;

import com.example.kanbansystem.Repository.BoardRepository;
import com.example.kanbansystem.Repository.TaskRepository;
import com.example.kanbansystem.entities.Board;
import com.example.kanbansystem.entities.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class BoardService {
    @Autowired
    private BoardRepository boardRepository;
    @Autowired
    private TaskRepository taskRepository;
    @Autowired
    private TaskService taskService;
    public Board getBoardByName(String name) {
        return boardRepository.findBoardByName(name);
    }
    public List<Board> getAllBoard(){
        return boardRepository.findAll();
    }
    public Board saveBoard(Board board){
        return boardRepository.save(board);
    }
    @Transactional
    public String deleteBoardById(Long boardId) {
        Optional<Board> optionalBoard = boardRepository.findById(boardId);
        if (optionalBoard.isPresent()) {
            Board board = optionalBoard.get();
            List<Task> tasks = taskRepository.findByBoardId(boardId);
            taskRepository.deleteAll(tasks);
            boardRepository.delete(board);
            return "Board and associated tasks deleted successfully";
        } else {
            return "Board not found";
        }
    }
    public Optional<Board> getBoardById(Long id) {
        return boardRepository.findById(id);
    }



}
