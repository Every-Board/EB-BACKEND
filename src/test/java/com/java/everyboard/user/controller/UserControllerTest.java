package com.java.everyboard.user.controller;

import com.java.everyboard.awsS3.AwsS3Service;
import com.java.everyboard.comment.repository.CommentRepository;
import com.java.everyboard.constant.HeartType;
import com.java.everyboard.constant.LoginType;
import com.java.everyboard.constant.ScrapType;
import com.java.everyboard.content.entity.ContentImage;
import com.java.everyboard.content.repository.ContentImageRepository;
import com.java.everyboard.content.repository.ContentRepository;
import com.java.everyboard.content.scrap.repository.ScrapRepository;
import com.java.everyboard.heart.commentheart.repository.CommentHeartRepository;
import com.java.everyboard.heart.contentHeart.repository.ContentHeartRepository;
import com.java.everyboard.response.SingleResponseDto;
import com.java.everyboard.user.dto.*;
import com.java.everyboard.user.entity.User;
import com.java.everyboard.user.entity.UserImage;
import com.java.everyboard.user.mapper.UserMapper;
import com.java.everyboard.user.repository.UserImageRepository;
import com.java.everyboard.user.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Validation;
import javax.validation.Validator;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

class UserControllerTest {
    @Mock
    private CommentRepository commentRepository;

    @Mock
    private ContentImageRepository contentImageRepository;

    @Mock
    private ContentRepository contentRepository;

    @Mock
    private UserService userService;

    @Mock
    private AwsS3Service awsService;

    @Mock
    private UserImageRepository userImageRepository;

    @Mock
    private ScrapRepository scrapRepository;

    @Mock
    private ContentHeartRepository contentHeartRepository;

    @Mock
    private CommentHeartRepository commentHeartRepository;

    @Mock
    private AwsS3Service awsS3Service;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private UserController userController;

    private Validator validator;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Test
    void postUser() {
        UserImage userImage = new UserImage();
        List<UserImage> profileUrl = new ArrayList<>();
        profileUrl.add(userImage);

        User user = new User();
        UserPostDto userPostDto = new UserPostDto("kang@gmail.com", "홍길동", "12345678aa!!");
        UserResponseDto userResponseDto = new UserResponseDto(1L, "kang@gmail.com", "홍길동", "12345678aa!!", profileUrl, LoginType.BASIC, LocalDateTime.now(), LocalDateTime.now());

        SingleResponseDto singleResponseDto = new SingleResponseDto(userResponseDto);

        // Arrange
        when(userService.createUser(any(User.class))).thenReturn(user);
        when(userMapper.postDtoToUser(any(UserPostDto.class))).thenReturn(user);
        when(userMapper.userToUserResponseDto(any(User.class),eq(userImageRepository))).thenReturn(userResponseDto);

        // Act
        ResponseEntity responseEntity = userController.postUser(userPostDto);

        // Assert
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals(singleResponseDto.toString(), responseEntity.getBody().toString());
        verify(userService, times(1)).createUser(user);
    }

    @Test
    void postProfile() {
        UserImage userImage = new UserImage();
        List<UserImage> userImages = new ArrayList<>();
        userImages.add(userImage);

        List<MultipartFile> images = new ArrayList<>();
        MockMultipartFile image1 = new MockMultipartFile("image", "1.png", "image/png", "test image".getBytes());
        images.add(image1);
        List<String> imgPaths = awsS3Service.uploadFile(images);

        long userId = 1L;

        User user = new User();
        UserPostImageDto userPostImageDto = new UserPostImageDto(1L,"1.png");
        UserResponseDto userResponseDto = new UserResponseDto(1L, "kang@gmail.com", "홍길동", "12345678aa!!", userImages, LoginType.BASIC, LocalDateTime.now(), LocalDateTime.now());

        SingleResponseDto singleResponseDto =new SingleResponseDto(userResponseDto);
        // Arrange
        when(userService.uploadProfile(any(User.class),anyList())).thenReturn(user);
        when(userMapper.postImageDtoToUser(any(UserPostImageDto.class))).thenReturn(user);
        when(userMapper.userToUserResponseDto(any(User.class),eq(userImageRepository))).thenReturn(userResponseDto);

        // Act
        ResponseEntity responseEntity = userController.postProfile(userId, userPostImageDto, images);

        // Assert
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals(singleResponseDto.toString(), responseEntity.getBody().toString());
        verify(userService, times(1)).uploadProfile(user,imgPaths);
    }

    @Test
    void patchUser() {
        UserImage userImage = new UserImage();
        List<UserImage> profileUrl = new ArrayList<>();
        profileUrl.add(userImage);

        long userId = 1L;
        User user = new User();
        UserPatchDto userPatchDto = new UserPatchDto(1L,"kang@gmail.com", "홍길동", "12345678aa!!","1.png");
        UserResponseDto userResponseDto = new UserResponseDto(1L, "kang@gmail.com", "홍길동", "12345678aa!!", profileUrl, LoginType.BASIC, LocalDateTime.now(), LocalDateTime.now());

        // Arrange
        when(userService.updateUser(any(User.class))).thenReturn(user);
        when(userMapper.patchDtoToUser(any(UserPatchDto.class))).thenReturn(user);
        when(userMapper.userToUserResponseDto(any(User.class),eq(userImageRepository))).thenReturn(userResponseDto);

        // Act
        ResponseEntity responseEntity = userController.patchUser(userId,userPatchDto);

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(userResponseDto, responseEntity.getBody());
        verify(userService, times(1)).updateUser(user);
    }

    @Test
    void getUser() {
        UserImage userImage = new UserImage();
        List<UserImage> profileUrl = new ArrayList<>();
        profileUrl.add(userImage);

        long userId = 1L;
        User user = new User();
        UserResponseDto userResponseDto = new UserResponseDto(1L, "kang@gmail.com", "홍길동", "12345678aa!!", profileUrl, LoginType.BASIC, LocalDateTime.now(), LocalDateTime.now());

        // Arrange
        when(userService.findUser(anyLong())).thenReturn(user);
        when(userMapper.userToUserResponseDto(any(User.class),eq(userImageRepository))).thenReturn(userResponseDto);

        // Act
        ResponseEntity responseEntity = userController.getUser(userId);

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(userResponseDto, responseEntity.getBody());
        verify(userService, times(1)).findUser(userId);
    }

    @Test
    void getUserMypage() {
        String profileUrl = "1.png";

        ContentImage contentImage = new ContentImage();
        List<ContentImage> contentImages = new ArrayList<>();
        contentImages.add(contentImage);

        UserImage userImage = new UserImage();
        List<UserImage> userImages = new ArrayList<>();
        userImages.add(userImage);

        UserContentResponseDto userContentResponseDto = new UserContentResponseDto(1L,"오늘 날씨 정말 좋다", "오늘 날씨 짱좋아", contentImages, LocalDateTime.now(),LocalDateTime.now());
        List<UserContentResponseDto> userContentResponseDtos = new ArrayList<>();
        userContentResponseDtos.add(userContentResponseDto);

        UserCommentResponseDto userCommentResponseDto = new UserCommentResponseDto(1L, 1L, "오늘 날씨 정말 좋다", "나도 그렇게 생각함", LocalDateTime.now(), LocalDateTime.now());
        List<UserCommentResponseDto> userCommentResponseDtos = new ArrayList<>();
        userCommentResponseDtos.add(userCommentResponseDto);

        UserScrapResponseDto userScrapResponseDto = new UserScrapResponseDto(1L, 1L, "오늘 날씨 짱이지 않냐?", "오늘 날씨 짱인듯", ScrapType.ADD, contentImages, LocalDateTime.now(), LocalDateTime.now());
        List<UserScrapResponseDto> userScrapResponseDtos = new ArrayList<>();
        userScrapResponseDtos.add(userScrapResponseDto);

        UserContentHeartResponseDto userContentHeartResponseDto = new UserContentHeartResponseDto(1L, "오늘 날씨 정말 좋다", HeartType.ADD,LocalDateTime.now(),LocalDateTime.now());
        List<UserContentHeartResponseDto> userContentHeartResponseDtos =new ArrayList<>();
        userContentHeartResponseDtos.add(userContentHeartResponseDto);

        long userId = 1L;
        User user = new User();
        UserAllResponseDto userAllResponseDto = new UserAllResponseDto(1L,"kang@gmail.com", "홍길동", "12345678aa!!", userImages,userContentResponseDtos,userCommentResponseDtos, userContentHeartResponseDtos,userScrapResponseDtos,LocalDateTime.now(), LocalDateTime.now());

        // Arrange
        when(userService.findUser(anyLong())).thenReturn(user);
        when(userMapper.userMyPage(any(User.class),eq(userImageRepository),eq(contentRepository),eq(contentImageRepository),eq(commentRepository),eq(contentHeartRepository),eq(scrapRepository))).thenReturn(userAllResponseDto);

        // Act
        ResponseEntity responseEntity = userController.getUserMypage(userId);

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(userAllResponseDto, responseEntity.getBody());
        verify(userService, times(1)).findUser(userId);
        verify(userMapper, times(1)).userMyPage(user,userImageRepository,contentRepository,contentImageRepository,commentRepository,contentHeartRepository,scrapRepository);
    }

    @Test
    void deleteUser() {
        // Arrange
        Long userId = 1L;

        // Act
        ResponseEntity responseEntity = userController.deleteUser(userId);

        // Assert
        assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
        verify(userService, times(1)).deleteUser(userId);
    }

    @Test
    void deleteProfile() {
        // Arrange
        Long userId = 1L;

        // Act
        ResponseEntity responseEntity = userController.deleteProfile(userId);

        // Assert
        assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
        verify(userService, times(1)).deleteProfile(userId);

    }
}