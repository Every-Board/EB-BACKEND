package com.java.everyboard.security.auth.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@NoArgsConstructor
@Getter
public class AuthEmailConfirmDto {
    @Email
    private String email;

    @NotBlank
    private String authCode;
}
