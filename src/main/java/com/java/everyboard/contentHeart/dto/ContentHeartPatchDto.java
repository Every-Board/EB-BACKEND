package com.java.everyboard.contentHeart.dto;

import com.java.everyboard.contentHeart.constant.HeartType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@AllArgsConstructor
@Getter
@Setter
public class ContentHeartPatchDto {
    @NotBlank
    private Long contentId;
    @NotBlank
    private HeartType heartType;
}