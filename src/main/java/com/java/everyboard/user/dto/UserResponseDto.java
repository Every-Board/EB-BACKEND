package com.java.everyboard.user.dto;

import com.java.everyboard.constant.LoginType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseDto {
    private Long userId;
    private String email;
    private String nickname;
    private String password;
    private String profileUrl;
    private LoginType loginType;
}
