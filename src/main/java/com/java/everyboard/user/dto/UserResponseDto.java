package com.java.everyboard.user.dto;

import com.java.everyboard.constant.LoginType;
import com.java.everyboard.user.entity.UserImage;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserResponseDto {
    private Long userId;
    private String email;
    private String nickname;
    private String password;
    private List<UserImage> profileUrl;
    private LoginType loginType;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
}
