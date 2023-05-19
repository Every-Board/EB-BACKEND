package com.java.everyboard.contentHeart.dto;

import com.java.everyboard.contentHeart.constant.HeartType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
@Setter
@Builder
public class ContentHeartListDto {
    private String title;
    private Long contentId;
    private HeartType heartType;
    private LocalDateTime createdAt;

    private LocalDateTime modifiedAt;
}