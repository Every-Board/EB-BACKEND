package com.java.everyboard.content.dto;

import com.java.everyboard.comment.dto.CommentResponseDto;
import com.java.everyboard.constant.Category;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@Getter
@Builder
public class ContentAllResponseDto {
    private Long contentId;
    private Long userId;
    private Long viewCount;
    private Long contentHeartCount;
    private String title;
    private String content;
    private String imageUrl; // 컨텐츠 이미지를 담는 주소
    private Category category;
    private String tag;
    private LocalDateTime createdAt; // 컨텐츠 작성시간
    private LocalDateTime modifiedAt; // 컨텐츠 수정 시간



    // 게시글 작성 유저 정보 //
    private String nickname;
    private String profileUrl; // 프로필 사진
    private List<CommentResponseDto> comments;
}
