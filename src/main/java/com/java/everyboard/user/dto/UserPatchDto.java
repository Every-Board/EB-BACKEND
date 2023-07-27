package com.java.everyboard.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserPatchDto {
    private Long userId;

    @Email(message = "올바른 형식으로 입력해야 합니다.")
    private String email;

    @Size(min = 2, max = 16, message = "닉네임 길이는 2글자 이상 6글자 이하여야 합니다")
    private String nickname;

    @Pattern(regexp = "(?=.*[0-9])(?=.*[a-zA-Z])(?=.*[!@#$%^&]).{8,16}", message = "비밀번호는 영문, 특수문자, 숫자 포함 8-16자 이내여야 합니다.")
    private String password;

    private String profileUrl;
}
