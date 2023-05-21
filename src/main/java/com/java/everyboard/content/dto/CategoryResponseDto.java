package com.java.everyboard.content.dto;

import com.java.everyboard.constant.Category;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
@Builder
public class CategoryResponseDto {
    private Long contentId;
    private Long userId;
    private String nickname;
    private Long viewCount;
    private Long contentHeartCount;
    private String title;
    private String content;
    private String imageUrl; // 컨텐츠 이미지를 담는 주소
    private Category category;
    private String tag;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
}