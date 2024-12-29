package com.example.kanbansystem.Service;

import com.example.kanbansystem.Repository.BoardRepository;
import com.example.kanbansystem.Repository.TaskRepository;
import com.example.kanbansystem.entities.Board;
import com.example.kanbansystem.entities.Task;
import com.example.kanbansystem.service.BoardService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TestBoardService {

    @Mock
    private BoardRepository boardRepository;

    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private BoardService boardService;

    @Test
    public void testGetBoardByName() {
        String boardName = "Test Board";
        Board mockBoard = new Board();
        mockBoard.setName(boardName);

        when(boardRepository.findBoardByName(boardName)).thenReturn(mockBoard);

        Board result = boardService.getBoardByName(boardName);

        assertNotNull(result);
        assertEquals(boardName, result.getName());
        verify(boardRepository, times(1)).findBoardByName(boardName);
    }

    @Test
    public void testGetAllBoard() {
        Board board1 = new Board();
        Board board2 = new Board();
        List<Board> mockBoards = Arrays.asList(board1, board2);

        when(boardRepository.findAll()).thenReturn(mockBoards);

        List<Board> result = boardService.getAllBoard();

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(boardRepository, times(1)).findAll();
    }

    @Test
    public void testSaveBoard() {
        Board mockBoard = new Board();
        mockBoard.setName("New Board");

        when(boardRepository.save(mockBoard)).thenReturn(mockBoard);

        Board result = boardService.saveBoard(mockBoard);

        assertNotNull(result);
        assertEquals("New Board", result.getName());
        verify(boardRepository, times(1)).save(mockBoard);
    }

    @Test
    public void testDeleteBoardById_Success() {
        Long boardId = 1L;
        Board mockBoard = new Board();
        mockBoard.setId(boardId);

        Task task1 = new Task();
        task1.setId(101L);
        Task task2 = new Task();
        task2.setId(102L);
        List<Task> mockTasks = Arrays.asList(task1, task2);

        when(boardRepository.findById(boardId)).thenReturn(Optional.of(mockBoard));
        when(taskRepository.findByBoardId(boardId)).thenReturn(mockTasks);

        String result = boardService.deleteBoardById(boardId);

        assertEquals("Board and associated tasks deleted successfully", result);
        verify(taskRepository, times(1)).deleteAll(mockTasks);
        verify(boardRepository, times(1)).delete(mockBoard);
    }

    @Test
    public void testDeleteBoardById_NotFound() {
        Long boardId = 1L;

        when(boardRepository.findById(boardId)).thenReturn(Optional.empty());

        String result = boardService.deleteBoardById(boardId);

        assertEquals("Board not found", result);
        verify(taskRepository, never()).deleteAll(anyList());
        verify(boardRepository, never()).delete(any(Board.class));
    }

    @Test
    public void testGetBoardById() {
        Long boardId = 1L;
        Board mockBoard = new Board();
        mockBoard.setId(boardId);

        when(boardRepository.findById(boardId)).thenReturn(Optional.of(mockBoard));

        Optional<Board> result = boardService.getBoardById(boardId);

        assertTrue(result.isPresent());
        assertEquals(boardId, result.get().getId());
        verify(boardRepository, times(1)).findById(boardId);
    }
}
