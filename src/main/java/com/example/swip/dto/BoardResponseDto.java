package com.example.swip.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class BoardResponseDto {
    private Long id;
    private String title;
    private String writer;
}
