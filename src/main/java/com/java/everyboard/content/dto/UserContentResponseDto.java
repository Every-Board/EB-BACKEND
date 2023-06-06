package com.java.everyboard.content.dto;

import com.java.everyboard.content.entity.ContentImage;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@Getter
@Setter
@Builder
public class UserContentResponseDto {
    private Long contentId;
    private Long userId;
    private String title;
    private String content;
    private List<ContentImage> contentImages;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    // 유저 정보 //
    private String nickname;
    private String profileUrl; // 프로필 사진

}
