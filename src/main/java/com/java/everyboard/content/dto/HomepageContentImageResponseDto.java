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
    private List<ContentImage> contentImageList; // 컨텐츠 이미지를 담는 주소
}