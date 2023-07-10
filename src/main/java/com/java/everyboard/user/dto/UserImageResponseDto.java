package com.java.everyboard.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor
public class UserImageResponseDto {
    private Long userId;
    private String profileUrl;
}
