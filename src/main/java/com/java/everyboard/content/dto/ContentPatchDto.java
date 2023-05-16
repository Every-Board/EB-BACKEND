package com.java.everyboard.content.dto;

import com.java.everyboard.content.contentEnum.Category;
import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@AllArgsConstructor
@Getter
public class ContentPatchDto {
    private Long contentId;
    @NotBlank(message = "게시글 제목을 입력해야 합니다.")
    private String title;
    @NotBlank(message = "게시글의 내용을 입력해야 합니다.")
    private String content;
    private String imageUrl; // 컨텐츠 이미지를 담는 주소
    @NotNull
    private Category category; // Enum으로 교체될 예정
    private String tag;
}
