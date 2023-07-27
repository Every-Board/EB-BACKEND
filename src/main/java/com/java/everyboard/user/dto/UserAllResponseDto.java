package com.java.everyboard.user.dto;

import com.java.everyboard.user.entity.UserImage;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Builder
@Getter
@AllArgsConstructor
public class UserAllResponseDto {
    private Long userId;
    private String email;
    private String nickname;
    private String password;
    private List<UserImage> profileUrl;

    private List<UserContentResponseDto> contents;

    private List<UserCommentResponseDto> comments;

    private List<UserContentHeartResponseDto> hearts;
    private List<UserScrapResponseDto> scraps;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

}