package com.java.everyboard.comment.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class CommentPostDto {
    private long contentId;
    private String comment;
}
