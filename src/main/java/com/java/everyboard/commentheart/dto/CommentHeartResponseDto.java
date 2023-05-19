package com.java.everyboard.commentheart.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
@Builder
public class CommentHeartResponseDto {

    private Long heartId;

    private Long contentId;

    private Long userId;

    private String heartType;
}
