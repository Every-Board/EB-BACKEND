package com.java.everyboard.comment.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
@Builder
public class CommentResponseDto {
    private long commentId;

    private long userId;

    private long contentId;

    private String comment;

    private String nickName;

    private LocalDateTime createdAt;

    private LocalDateTime modifiedAt;
}
