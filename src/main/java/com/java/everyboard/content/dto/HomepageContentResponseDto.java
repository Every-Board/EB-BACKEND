package com.java.everyboard.content.dto;

import com.java.everyboard.constant.Category;
import com.java.everyboard.content.entity.Content;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
@Setter
@Builder
public class HomepageContentResponseDto {
    private Long contentId;
    private Long userId;
    @NotBlank(message = "게시글 제목을 입력해야 합니다.")
    private String title;
    private String imageUrl; // 컨텐츠 이미지를 담는 주소
    private Long viewCount;
    @NotNull
    private Category category; // Enum으로 교체될 예정
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    public HomepageContentResponseDto(Content entity) {
        this.contentId = entity.getContentId();
        this.userId = entity.getContentId();
        this.title = entity.getTitle();
        this.imageUrl = entity.getImageUrl();
        this.viewCount = entity.getViewCount();
        this.category = entity.getCategory();
        this.createdAt = entity.getCreatedAt();
        this.modifiedAt = entity.getModifiedAt();
    }
}
