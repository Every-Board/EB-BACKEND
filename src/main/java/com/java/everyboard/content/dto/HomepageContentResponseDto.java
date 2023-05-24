package com.java.everyboard.content.dto;

import com.java.everyboard.constant.Category;
import com.java.everyboard.content.entity.Content;
import com.java.everyboard.content.entity.ContentImage;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@Getter
@Setter
@Builder
public class HomepageContentResponseDto {
    private Long contentId;
    private Long userId;
    private String title;
    private List<ContentImage> contentImageList; // 컨텐츠 이미지를 담는 주소
    private Long viewCount;
    private Category category;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
}
