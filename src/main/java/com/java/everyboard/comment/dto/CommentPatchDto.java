package com.java.everyboard.comment.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class CommentPatchDto {

    private long commentId;

    private long userId;

    private long contentId;

    private String comment;

    public void setCommentId(long commentId) {
        this.commentId = commentId;
    }
}
