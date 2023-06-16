package com.java.everyboard.security.auth.controller;

import com.java.everyboard.constant.AuthProvider;
import com.java.everyboard.exception.ExceptionCode;
import com.java.everyboard.security.auth.dto.*;
import com.java.everyboard.security.auth.service.AuthService;
import com.java.everyboard.security.dto.LoginDto;
import com.java.everyboard.user.dto.UserPostDto;
import com.java.everyboard.user.entity.User;
import com.java.everyboard.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.net.URI;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Validated
public class AuthController {

    private final AuthService authService;
    private AuthenticationManager authenticationManager;
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
//    private TokenProvider tokenProvider;

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginDto loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getEmail(),
                        loginRequest.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

//        String token = tokenProvider.createToken(authentication);
        String token = authService.reissuedToken(String.valueOf(authentication));
        return ResponseEntity.ok(new AuthResponse(token));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody UserPostDto signUpRequest) {
        if(userRepository.findByEmail(signUpRequest.getEmail()).isPresent()) {
            return ResponseEntity.ok(ExceptionCode.EMAIL_EXISTS);
        }

        // Creating user's account
        User user = new User();
        user.setNickname(signUpRequest.getNickname());
        user.setEmail(signUpRequest.getEmail());
        user.setPassword(signUpRequest.getPassword());
        user.setAuthProvider(AuthProvider.local);

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        User result = userRepository.save(user);

        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/user/me")
                .buildAndExpand(result.getUserId()).toUri();

        return ResponseEntity.created(location)
                .body(new ApiResponse(true, "User registered successfully@"));
    }

    //로그아웃
    @PostMapping("/logout")
    public ResponseEntity logout(@RequestHeader("Authorization") @NotBlank String accessToken) {
        authService.logout(accessToken);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    //토큰 재발급
    @PostMapping("/token")
    public ResponseEntity reissueAccessToken(@RequestHeader("Refresh") @NotBlank String refreshToken,
                                             HttpServletResponse response) {

        String newAccessToken = authService.reissuedToken(refreshToken);
        response.setHeader("Authorization", "Bearer " + newAccessToken);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    //비밀번호 찾기시 랜덤 링크 전송
    @PostMapping("/password")
    public ResponseEntity findPassword(@RequestBody @Valid AuthEmailDto emailDto) throws MessagingException {
        authService.sendEmailForPassword(emailDto.getEmail());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    //랜덤 링크를 통한 비밀번호 변경
    @PatchMapping("/password")
    public ResponseEntity authPasswordUrl(@RequestParam @NotBlank String authCode,
                                          @RequestBody @Valid AuthPasswordDto passwordDto) {
        authService.setNewPassword(authCode, passwordDto.getNewPassword());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    //회원가입 이메일 인증번호 전송
    @PostMapping("/email")
    public ResponseEntity authEmail(@RequestBody @Valid AuthEmailDto emailDto) throws MessagingException {
        authService.sendEmailForJoin(emailDto.getEmail());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    // 인가코드 서버에 전송
    @PostMapping("/email/confirm")
    public ResponseEntity confirmEmail(@RequestBody @Valid AuthEmailConfirmDto confirmDto) {
        authService.authorizedEmail(confirmDto.getAuthCode(), confirmDto.getEmail());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    //관리자에게 문의하기
    @PostMapping("/email/query")
    public ResponseEntity queryEmail(@RequestBody @Valid AuthEmailQuestionDto questionDto) throws MessagingException {
        authService.sendEmailForQuery(questionDto.getContent());
        return new ResponseEntity<>(HttpStatus.OK);
    }
}