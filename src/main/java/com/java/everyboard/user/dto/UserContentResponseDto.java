package com.java.everyboard.user.dto;

import com.java.everyboard.content.entity.ContentImage;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@Getter
@Setter
@Builder
public class UserContentResponseDto {
    private Long contentId;
    private String title;
    private String content;
    private List<ContentImage> contentImages;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
}
