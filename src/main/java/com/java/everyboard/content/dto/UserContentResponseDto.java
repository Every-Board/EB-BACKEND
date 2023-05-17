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
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    // 유저 정보 //
    private String nickname;
    private String profileUrl; // 프로필 사진

}
