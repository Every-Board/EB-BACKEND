package com.java.everyboard.reply.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ReplyPostDto {
    private long contentId;
    private long commentId;
    private String reply;
}
