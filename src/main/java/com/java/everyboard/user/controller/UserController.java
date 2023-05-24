package com.java.everyboard.user.controller;

import com.java.everyboard.user.dto.UserPatchDto;
import com.java.everyboard.user.dto.UserPostDto;
import com.java.everyboard.user.entity.User;
import com.java.everyboard.user.mapper.UserMapper;
import com.java.everyboard.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {
    private final UserService userService;
    private final UserMapper userMapper;

    // 회원 가입
    @PostMapping("/join")
    public ResponseEntity postUser(@RequestBody @Valid UserPostDto requestBody) {
        User user = userService.createUser(userMapper.postDtoToUser(requestBody));
        return new ResponseEntity<>(userMapper.userToUserResponseDto(user), HttpStatus.CREATED);
    }

    // 회원 수정
    @PatchMapping("/{userId}")
    public ResponseEntity patchUser(@PathVariable("userId") @Positive Long userId,
                                    @RequestBody @Valid UserPatchDto requestBody) {
        requestBody.setUserId(userId);

        User user = userService.updateUser(userMapper.patchDtoToUser(requestBody));
        user.setUserId(userId);

        return new ResponseEntity<>(userMapper.userToUserResponseDto(user), HttpStatus.OK);
    }

    // 회원 단건 조회
    @GetMapping("/{userId}")
    public ResponseEntity getUser(@PathVariable("userId") @Positive Long userId) {
        User user = userService.findUser(userId);
        return new ResponseEntity<>(userMapper.userToUserResponseDto(user), HttpStatus.OK);
    }

    // 회원 탈퇴
    @DeleteMapping("/{userId}")
    public ResponseEntity deleteUser(@PathVariable("userId") @Positive Long userId) {
        userService.deleteUser(userId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
