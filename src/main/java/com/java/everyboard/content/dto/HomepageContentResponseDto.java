package com.java.everyboard.content.dto;

import com.java.everyboard.content.contentEnum.Category;
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
    @NotNull
    private Category category; // Enum으로 교체될 예정
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

}
