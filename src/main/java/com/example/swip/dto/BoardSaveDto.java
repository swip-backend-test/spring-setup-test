package com.example.swip.dto;

import com.example.swip.entity.Board;
import lombok.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BoardSaveDto {

    private String title;
    private String content;
    private String writer;

    public Board toEntity() {
        return Board.builder()
                .title(this.title)
                .content(this.content)
                .writer(this.writer)
                .build();
    }
}
