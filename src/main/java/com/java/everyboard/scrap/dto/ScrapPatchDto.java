package com.java.everyboard.scrap.dto;

import com.java.everyboard.constant.ScrapType;
import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class ScrapPatchDto {
    @NotBlank
    private Long contentId;
    private String title;
    @NotBlank
    private ScrapType scrapType;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
}