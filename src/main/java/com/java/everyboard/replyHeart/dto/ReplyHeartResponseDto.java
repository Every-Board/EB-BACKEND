package com.java.everyboard.replyHeart.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
@Builder
public class ReplyHeartResponseDto {
    private Long replyHeartId;
    private Long replyId;
    private Long userId;
    private String heartType;
}
