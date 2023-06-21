package com.java.everyboard.content.scrap.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
@Setter
@Builder
public class ScrapListResponseDto {
    private Long scrapId;
    private Long suerId;
    private Long contentId;
    private String scrapType ;
}