package com.java.everyboard.content.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
@Setter
@Builder
public class UserContentResponseDto {
    private Long contentId;
    private Long userId;
    @NotBlank(message = "게시글 제목을 입력해야 합니다.")
    private String title;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

}
