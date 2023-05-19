package com.java.everyboard.scrap.dto;

import com.java.everyboard.scrap.constant.ScrapType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
@Setter
@Builder
public class ScrapResponseDto {
    private Long scrapId;
    private Long userId;
    private Long contentId;
    private String scrapType ;
    private String title ;
    private String content ;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
}