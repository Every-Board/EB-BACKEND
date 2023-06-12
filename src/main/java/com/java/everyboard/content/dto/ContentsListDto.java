package com.java.everyboard.content.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
@Builder
public class ContentsListDto {
    List<ContentResponseDto> contentResponseDto;
}
