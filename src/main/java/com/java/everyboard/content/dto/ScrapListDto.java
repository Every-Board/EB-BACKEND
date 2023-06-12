package com.java.everyboard.content.dto;

import com.java.everyboard.scrap.entity.Scrap;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
@AllArgsConstructor
@Getter
@Builder
public class ScrapListDto {
//    private Scrap scrap;
    private List<ScrapResponseDto> contents;
}
