package com.example.swip.service;

import com.example.swip.dto.BoardSaveDto;
import com.example.swip.dto.BoardUpdateDto;
import com.example.swip.entity.Board;
import com.example.swip.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;


    //저장
    @Transactional
    public Long saveBoard(BoardSaveDto boardSaveDto){
        Board savedBoard = boardRepository.save(boardSaveDto.toEntity());
        return savedBoard.getId();
    }

    //조회
    public Board findBoard(Long id){
        Board findBoard = boardRepository.findById(id).orElse(null);
        return findBoard;
    }

    public List<Board> findAllBoards(){
        List<Board> allBoards = boardRepository.findAll();
        return allBoards;
    }

    //수정
    @Transactional
    public Long updateBoard(Long id, BoardUpdateDto boardUpdateDto) {
        Board findBoard = boardRepository.findById(id).orElse(null);

        // JPA의 영속성 컨텍스트에 의해 entity 객체의 값만 변경하면 자동으로 변경 사항 반영하여 update 진행.
        // reoisitory.update 필요 없음.
        if(findBoard != null){
            findBoard.updateBoard(boardUpdateDto.getTitle(), boardUpdateDto.getContent());
        }
        // TODO: null 예외 처리

        return id;
    }
    //삭제
    @Transactional
    public Long deleteBoard(Long id){
        Board findBoard = boardRepository.findById(id).orElse(null);
        boardRepository.delete(findBoard);

        return id;
    }
}
