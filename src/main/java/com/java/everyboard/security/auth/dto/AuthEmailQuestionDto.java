package com.java.everyboard.security.auth.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@NoArgsConstructor
@Getter
public class AuthEmailQuestionDto {
    @NotBlank(message = "내용을 입력하세요.")
    private String content;
}
