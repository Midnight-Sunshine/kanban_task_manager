package com.example.kanbansystem.Controller;
import com.example.kanbansystem.controller.BoardController;
import com.example.kanbansystem.entities.Board;
import com.example.kanbansystem.entities.User;
import com.example.kanbansystem.security.MyuserDetailsService;
import com.example.kanbansystem.service.BoardService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TestBoardController {

    @Mock
    private BoardService boardService;

    @InjectMocks
    private BoardController boardController;

    @Mock
    private MyuserDetailsService userService;

    @Test
    public void testGetAllBoards() {
        Board board1 = new Board();
        Board board2 = new Board();
        List<Board> mockBoards = Arrays.asList(board1, board2);
        when(boardService.getAllBoard()).thenReturn(mockBoards);

        ResponseEntity<List<Board>> responseEntity = boardController.getAllBoard();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(mockBoards, responseEntity.getBody());
    }

    @Test
    public void testGetBoardByName_Found() {
        String boardName = "Board 1";
        Board mockBoard = new Board();
        when(boardService.getBoardByName(boardName)).thenReturn(mockBoard);

        ResponseEntity<Board> responseEntity = boardController.getBoardByName(boardName);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(mockBoard, responseEntity.getBody());
    }

    @Test
    public void testGetBoardByName_NotFound() {
        String boardName = "Nonexistent Board";
        when(boardService.getBoardByName(boardName)).thenReturn(null);

        ResponseEntity<Board> responseEntity = boardController.getBoardByName(boardName);

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }

    @Test
    public void testGetBoardById_Found() {
        Long boardId = 1L;
        Optional<Board> mockOptionalBoard = Optional.of(new Board());
        when(boardService.getBoardById(boardId)).thenReturn(mockOptionalBoard);

        ResponseEntity<Board> responseEntity = boardController.getBoardById(boardId);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(mockOptionalBoard.get(), responseEntity.getBody());
    }

    @Test
    public void testGetBoardById_NotFound() {
        Long boardId = 1L;
        when(boardService.getBoardById(boardId)).thenReturn(Optional.empty());

        ResponseEntity<Board> responseEntity = boardController.getBoardById(boardId);

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }

    @Test
    public void testSaveBoard_ValidUser() {
        Board mockBoard = new Board();
        mockBoard.setUserId(1L);
        User mockUser = new User();

        when(userService.getUserById(1L)).thenReturn(Optional.of(mockUser));
        when(boardService.saveBoard(any(Board.class))).thenReturn(mockBoard);

        ResponseEntity<Board> responseEntity = boardController.saveBoard(mockBoard);

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals(mockBoard, responseEntity.getBody());
    }

    @Test
    public void testSaveBoard_InvalidUser() {
        Board mockBoard = new Board();
        mockBoard.setUserId(1L);

        when(userService.getUserById(1L)).thenReturn(Optional.empty());

        ResponseEntity<Board> responseEntity = boardController.saveBoard(mockBoard);

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    }

    @Test
    public void testSaveBoard_NullUserId() {
        Board mockBoard = new Board();
        mockBoard.setUserId(null);

        ResponseEntity<Board> responseEntity = boardController.saveBoard(mockBoard);

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    }

    @Test
    public void testDeleteBoardById_Success() {
        Long boardId = 1L;
        String expectedResult = "Deleted";

        when(boardService.deleteBoardById(boardId)).thenReturn(expectedResult);

        ResponseEntity<String> responseEntity = boardController.deleteBoardById(boardId);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(expectedResult, responseEntity.getBody());
    }

    @Test
    public void testDeleteBoardById_NotFound() {
        Long boardId = 1L;
        String expectedResult = "Board not found";

        when(boardService.deleteBoardById(boardId)).thenReturn(expectedResult);

        ResponseEntity<String> responseEntity = boardController.deleteBoardById(boardId);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(expectedResult, responseEntity.getBody());
    }
}
