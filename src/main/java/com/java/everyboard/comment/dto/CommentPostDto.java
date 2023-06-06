package com.java.everyboard.comment.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class CommentPostDto {
    private long contentId;

    private String comment;
}
