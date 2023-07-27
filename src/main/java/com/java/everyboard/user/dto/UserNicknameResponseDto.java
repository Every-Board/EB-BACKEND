package com.java.everyboard.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor
public class UserNicknameResponseDto {
    private long userId;
    private String nickname;
}
