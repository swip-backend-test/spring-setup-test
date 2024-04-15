package com.example.swip.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class BoardRepositoryTest {

    @Autowired
    BoardRepository boardRepository;

    @Test
    public void 게시글저장(){
        //given
        //when
        //then
    }

    @Test
    public void 게시글_하나_조회(){
        //given
        //when
        //then
    }

    @Test
    public void 게시글_전체_조회(){
        //given
        //when
        //then
    }

    @Test
    public void 게시글_수정(){
        //given
        //when
        //then
    }

    @Test
    public void 게시글_하나_삭제(){
        //given
        //when
        //then
    }

    // 나중에 필요하면 구현
    @Test
    public void 게시글_여러개_선택_삭제(){
        //given
        //when
        //then
    }


}