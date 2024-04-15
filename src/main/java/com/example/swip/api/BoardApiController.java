package com.example.swip.api;

import com.example.swip.dto.BoardDetailResponseDto;
import com.example.swip.dto.BoardResponseDto;
import com.example.swip.dto.BoardSaveDto;
import com.example.swip.dto.BoardUpdateDto;
import com.example.swip.entity.Board;
import com.example.swip.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class BoardApiController {

    private final BoardService boardService;

    //저장
    @PostMapping("/board")
    public Long saveBoard(@RequestBody BoardSaveDto dto){

        Long savedBoard = boardService.saveBoard(dto);
        System.out.println("savedBoard = " + savedBoard);
        return savedBoard;
    }

    //조회
    @GetMapping("/board")
    public List<BoardResponseDto> showBoard(){
        List<Board> allBoards = boardService.findAllBoards();

        //Dto 로 변환
        List<BoardResponseDto> result = allBoards.stream()
                .map(board -> new BoardResponseDto(board.getId(), board.getTitle(), board.getWriter()))
                .collect(Collectors.toList());

        return result; // TODO: Result 타입으로 한번 감싸기
    }


    @GetMapping("/board/{id}")
    public BoardDetailResponseDto showBoardDetail(@PathVariable("id") Long boardId){
        Board findBoard = boardService.findBoard(boardId);

        // Dto 로 변환
        BoardDetailResponseDto response = new BoardDetailResponseDto(
                findBoard.getId(),
                findBoard.getTitle(),
                findBoard.getContent(),
                findBoard.getWriter(),
                findBoard.getCreated_time(),
                findBoard.getUpdated_time());

        return response;
    }

    //수정
    @PutMapping("/board/{id}/edit")
    public Long updateBoardDetail(
            @PathVariable("id") Long boardId,
            @RequestBody BoardUpdateDto boardUpdateDto
    ){
        Long updatedBoardId = boardService.updateBoard(boardId, boardUpdateDto);
        return updatedBoardId;
    }

    //삭제
    @DeleteMapping("/board/{id}")
    public Long deleteBoardDetail(@PathVariable("id") Long boardId){
        Long deletedBoardId = boardService.deleteBoard(boardId);
        return deletedBoardId;
    }



}
