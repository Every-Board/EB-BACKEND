package com.java.everyboard.content.dto;

import com.java.everyboard.content.entity.ContentImage;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@Getter
@Builder
public class ScrapResponseDto {
    private Long contentId;
    private Long userId;
    private String nickname;
    private Long viewCount;
    private Long contentHeartCount;
    private String title;
    private String content;
    private List<ContentImage> contentImages; // 컨텐츠 이미지를 담는 주소
//    private Scrap scrap;
    //    private String tag;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
}
