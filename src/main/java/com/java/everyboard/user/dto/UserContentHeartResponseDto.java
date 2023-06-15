package com.java.everyboard.user.dto;

import com.java.everyboard.constant.HeartType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
@Setter
@Builder
public class UserContentHeartResponseDto {
    private Long contentId;
    private String title;
    private HeartType heartType;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
}
