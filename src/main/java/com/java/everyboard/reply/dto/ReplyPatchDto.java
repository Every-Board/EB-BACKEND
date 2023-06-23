package com.java.everyboard.reply.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ReplyPatchDto {
    private long replyId;
    private long contentId;
    private long commentId;
    private long userId;
    private String reply;

    public void setReplyId(long replyId) {
        this.replyId = replyId;
    }
}
