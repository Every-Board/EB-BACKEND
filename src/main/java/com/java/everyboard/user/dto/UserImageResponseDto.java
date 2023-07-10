package com.java.everyboard.user.dto;

import com.java.everyboard.user.entity.UserImage;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
@AllArgsConstructor
public class UserImageResponseDto {
    private Long userId;
    private List<UserImage> profileUrl;
}
