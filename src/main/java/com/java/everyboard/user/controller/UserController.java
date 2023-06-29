package com.java.everyboard.user.controller;

import com.java.everyboard.awsS3.AwsS3Service;
import com.java.everyboard.comment.repository.CommentRepository;
import com.java.everyboard.content.repository.ContentImageRepository;
import com.java.everyboard.content.repository.ContentRepository;
import com.java.everyboard.heart.contentHeart.repository.ContentHeartRepository;
import com.java.everyboard.exception.BusinessLogicException;
import com.java.everyboard.exception.ExceptionCode;
import com.java.everyboard.response.SingleResponseDto;
import com.java.everyboard.content.scrap.repository.ScrapRepository;
import com.java.everyboard.user.dto.UserPatchDto;
import com.java.everyboard.user.dto.UserPostDto;
import com.java.everyboard.user.dto.UserPostImageDto;
import com.java.everyboard.user.dto.UserResponseDto;
import com.java.everyboard.user.entity.User;
import com.java.everyboard.user.mapper.UserMapper;
import com.java.everyboard.user.repository.UserImageRepository;
import com.java.everyboard.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.util.List;

@Validated
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {
    private final UserService userService;
    private final UserMapper userMapper;
    private final ContentRepository contentRepository;
    private final ContentImageRepository contentImageRepository;
    private final CommentRepository commentRepository;
    private final ContentHeartRepository contentHeartRepository;
    private final ScrapRepository scrapRepository;
    private final UserImageRepository userImageRepository;
    private final AwsS3Service awsS3Service;

    // 회원 가입
    @CrossOrigin
    @PostMapping("/join")
    public ResponseEntity postUser(@Valid @RequestBody UserPostDto requestBody) {


        User user = userService.createUser(userMapper.postDtoToUser(requestBody));
        UserResponseDto userResponseDto = userMapper.userToUserResponseDto(user, userImageRepository);

        return new ResponseEntity<>(
                new SingleResponseDto<>(userResponseDto) , HttpStatus.CREATED
        );
    }

    // 프로필 이미지 업로드
    @PostMapping(value = "/{userId}/profile",consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity postProfile(@PathVariable("userId") @Positive Long userId, @RequestPart("data") UserPostImageDto requestBody,
                                      @RequestPart(required = false, value = "ProfileUrl") List<MultipartFile> multipartfiles) {

        requestBody.setUserId(userId);

        if (multipartfiles == null) {
            throw new BusinessLogicException(ExceptionCode.PROFILE_IMAGE_NOT_FOUND);
        }
        List<String> profileImgPath = awsS3Service.uploadFile(multipartfiles);
        log.info("IMG 경로들 : "+ profileImgPath);

        User user = userService.uploadProfile(userMapper.postImageDtoToUser(requestBody),profileImgPath);
        UserResponseDto userResponseDto = userMapper.userToUserResponseDto(user, userImageRepository);

        return new ResponseEntity<>(
                new SingleResponseDto<>(userResponseDto) , HttpStatus.CREATED
        );
    }

    // 회원 수정
    @PatchMapping("/{userId}")
    public ResponseEntity patchUser(@PathVariable("userId") @Positive Long userId,
                                    @RequestBody @Valid UserPatchDto requestBody) {
        requestBody.setUserId(userId);

        User user = userService.updateUser(userMapper.patchDtoToUser(requestBody));
        user.setUserId(userId);

        UserResponseDto userResponseDto = userMapper.userToUserResponseDto(user, userImageRepository);

        return new ResponseEntity<>(userResponseDto, HttpStatus.OK);
    }

    // 회원 단건 조회
    @GetMapping("/{userId}")
    public ResponseEntity getUser(@PathVariable("userId") @Positive Long userId) {
        User user = userService.findUser(userId);
        return new ResponseEntity<>(userMapper.userToUserResponseDto(user,userImageRepository), HttpStatus.OK);
    }

    // 회원 마이페이지 조회
    @GetMapping("/{userId}/mypage")
    public ResponseEntity getUserMypage(@PathVariable("userId") @Positive Long userId) {
        User user = userService.findUser(userId);
        return new ResponseEntity<>(userMapper.userMyPage(user, userImageRepository, contentRepository, contentImageRepository, commentRepository, contentHeartRepository, scrapRepository), HttpStatus.OK);
    }

    // 회원 닉네임 조회
    @GetMapping("/{userId}/nickname")
    public ResponseEntity getUserNickname(@PathVariable("userId") @Positive Long userId) {
        User user = userService.findUser(userId);
        return new ResponseEntity<>(userMapper.userToUserNicknameResponseDto(user), HttpStatus.OK);
    }

    // 회원 탈퇴
    @DeleteMapping("/{userId}")
    public ResponseEntity deleteUser(@PathVariable("userId") @Positive Long userId) {
        userService.deleteUser(userId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // 프로필 삭제
    @DeleteMapping("/{userId}/profile")
    public ResponseEntity deleteProfile(@PathVariable("userId") @Positive Long userId) {
        userService.deleteProfile(userId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}