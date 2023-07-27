package com.java.everyboard.heart.commentheart.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
@Builder
public class CommentHeartResponseDto {

    private Long commentHeartId;

    private Long contentId;
    private Long commentId;

    private Long userId;

    private String heartType;
}
