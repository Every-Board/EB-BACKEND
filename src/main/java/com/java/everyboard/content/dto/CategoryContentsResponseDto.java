package com.java.everyboard.content.dto;

import com.java.everyboard.constant.Category;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@Getter
@Builder
public class CategoryContentsResponseDto {
    private Category category;
    private List<CategoryResponseDto> contents;
}
