package com.java.everyboard.heart.contentHeart.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
@Builder
public class ContentHeartResponseDto {
    private Long contentHeartId;
    private Long contentId;
    private Long userId;
    private String heartType;
}
