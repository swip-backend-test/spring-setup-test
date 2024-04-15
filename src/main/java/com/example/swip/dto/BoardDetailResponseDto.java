package com.example.swip.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
public class BoardDetailResponseDto {
    private Long id;
    private String title;
    private String content;
    private String writer;
    private LocalDateTime created_time;
}
