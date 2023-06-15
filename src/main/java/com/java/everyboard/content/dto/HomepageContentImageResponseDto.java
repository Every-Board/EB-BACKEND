package com.java.everyboard.content.dto;

import com.java.everyboard.content.entity.ContentImage;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@Getter
@Setter
@Builder
public class HomepageContentImageResponseDto {
    private Long contentId;
    private String title;
    private String content;
    private Long viewCount;
    private List<ContentImage> contentImages; // 컨텐츠 이미지를 담는 주소
}