package com.java.everyboard.user.dto;

import com.java.everyboard.constant.ScrapType;
import com.java.everyboard.content.entity.ContentImage;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
@AllArgsConstructor
@Getter
@Setter
@Builder
public class UserScrapResponseDto {
    private Long scrapId;
    private Long contentId;
    private String title;
    private String content;
    private ScrapType scrapType;
    private List<ContentImage> contentImages;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
}
