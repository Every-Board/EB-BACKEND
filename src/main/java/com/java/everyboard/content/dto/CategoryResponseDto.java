package com.java.everyboard.content.dto;

import com.java.everyboard.constant.Category;
import com.java.everyboard.content.entity.ContentImage;
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
public class CategoryResponseDto {
    private Long contentId;
    private Long userId;
    private String nickname;
    private Long viewCount;
    private Long contentHeartCount;
    private String title;
    private String content;
    private List<ContentImage> contentImageList; // 컨텐츠 이미지를 담는 주소
    private Category category;
    private String tag;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
}
