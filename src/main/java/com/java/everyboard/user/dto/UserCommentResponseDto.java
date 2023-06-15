package com.java.everyboard.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
@Builder
public class UserCommentResponseDto {
    private long commentId;
    private long contentId;
    private String title;
    private String comment;
    private LocalDateTime createdAt;

    private LocalDateTime modifiedAt;
}