package com.java.everyboard.content.dto;

import com.java.everyboard.constant.Category;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
@Builder
public class ContentAllResponseDto {
    private Long contentId;
    private Long userId;
    private Long viewCount;
    private Long heartCount;
    @NotBlank(message = "게시글 제목을 입력해야 합니다.")
    private String title;
    @NotBlank(message = "게시글의 내용을 입력해야 합니다.")
    private String content;
    private String imageUrl; // 컨텐츠 이미지를 담는 주소
    @NotNull
    private Category category; // Enum으로 교체될 예정
    private String tag;
    private LocalDateTime createdAt; // 컨텐츠 작성시간
    private LocalDateTime modifiedAt; // 컨텐츠 수정 시간



    // 게시글 작성 유저 정보 //
    private String nickname;
    private String profileUrl; // 프로필 사진
//    private List<CommentResponseDto> comments;
}
