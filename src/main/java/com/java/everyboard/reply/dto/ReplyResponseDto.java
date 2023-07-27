package com.java.everyboard.reply.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
@Builder
public class ReplyResponseDto {
    private long replyId;
    private long userId;
    private long contentId;
    private long commentId;
//    private String title;
//    private String comment;
    private String reply;
    private String nickName;
    private long replyHeartCount;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
}
