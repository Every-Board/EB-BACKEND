//package com.java.everyboard.content.controller;
//
//import com.java.everyboard.AwsS3.AwsS3Service;
//import com.java.everyboard.comment.dto.CommentResponseDto;
//import com.java.everyboard.comment.repository.CommentRepository;
//import com.java.everyboard.content.dto.ContentAllResponseDto;
//import com.java.everyboard.content.dto.ContentResponseDto;
//import com.java.everyboard.content.entity.Content;
//import com.java.everyboard.constant.Category;
//import com.java.everyboard.content.dto.ContentPostDto;
//import com.java.everyboard.content.entity.ContentImage;
//import com.java.everyboard.content.mapper.ContentMapper;
//import com.java.everyboard.content.repository.ContentImageRepository;
//import com.java.everyboard.content.repository.ContentRepository;
//import com.java.everyboard.content.service.ContentService;
//import com.java.everyboard.user.entity.UserImage;
//import com.java.everyboard.user.repository.UserImageRepository;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.mock.web.MockMultipartFile;
//import org.springframework.security.test.context.support.WithMockUser;
//import org.springframework.web.multipart.MultipartFile;
//
//import javax.transaction.Transactional;
//import javax.validation.Validation;
//import javax.validation.Validator;
//
//import java.time.LocalDateTime;
//import java.util.ArrayList;
//import java.util.List;
//
//import static org.mockito.ArgumentMatchers.any;
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.*;
//
//@Transactional
//@SpringBootTest
//@AutoConfigureMockMvc
//@WithMockUser
//class ContentControllerTest {
//    @Mock
//    private ContentService contentService;
//
//
//    @Mock
//    private ContentMapper contentMapper;
//
//    @Mock
//    private ContentImageRepository contentImageRepository;
//
//    @Mock
//    private UserImageRepository userImageRepository;
//
//    @Mock
//    private CommentRepository commentRepository;
//
//    @Mock
//    private ContentRepository contentRepository;
//
//    @Mock
//    private AwsS3Service awsS3Service;
//
//    @InjectMocks
//    private ContentController contentController;
//
//    private Validator validator;
//
//    @BeforeEach
//    void setUp() {
//        MockitoAnnotations.openMocks(this);
//        validator = Validation.buildDefaultValidatorFactory().getValidator();
//    }
//
//    @DisplayName("POST 게시글 등록")
//    @Test
//    void postContent() throws Exception {
//        List<MultipartFile> images = new ArrayList<>();
//        MockMultipartFile image1 = new MockMultipartFile("image", "1.png", "image/png", "test image".getBytes());
//        MockMultipartFile image2 = new MockMultipartFile("image", "2.png", "image/png", "test image".getBytes());
//        images.add(image1);
//        images.add(image2);
//        List<String> imgPaths = awsS3Service.uploadFile(images);
//
//        CommentResponseDto commentResponseDto = new CommentResponseDto(1L,1L,1L,"좋습니다~!","최고입니다~","홍길동",LocalDateTime.now(),LocalDateTime.now());
//        List<CommentResponseDto> comments = new ArrayList<>();
//        comments.add(commentResponseDto);
//
//        ContentImage contentImage = new ContentImage(1L,"1.png");
//        List<ContentImage> contentImages = new ArrayList<>();
//        contentImages.add(contentImage);
//
//        UserImage userImage = new UserImage(1L, "2.png");
//        List<UserImage> userImages = new ArrayList<>();
//        userImages.add(userImage);
//
//        ContentPostDto contentPostDto = new ContentPostDto(1L, "오늘의 날씨 소개","오늘 날씨 너무 좋다", Category.자유게시판, contentImages);
//        Content content = new Content();
//        ContentAllResponseDto contentAllResponseDto = new ContentAllResponseDto(1L,1L, 0L, 0L,"오늘의 날씨 소개", "오늘 날씨 너무 좋다", contentImages,Category.자유게시판, LocalDateTime.now(),LocalDateTime.now(),"홍길동",userImages,comments);
//
//        when(contentMapper.contentPostDtoToContent(any(ContentPostDto.class))).thenReturn(content);
//        when(contentService.createContent(any(Content.class), anyList())).thenReturn(content);
//        when(contentMapper.contentToContentAllResponse(any(Content.class),eq(commentRepository),eq(contentImageRepository),eq(userImageRepository))).thenReturn(contentAllResponseDto);
//
//        // Act
//        ResponseEntity responseEntity = contentController.postContent(contentPostDto,images);
//
//        // Assert
//        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
//        assertEquals(contentAllResponseDto, responseEntity.getBody());
//        verify(contentService, times(1)).createContent(content, imgPaths);
//    }
//
//    @Test
//    void getContent() {
//        CommentResponseDto commentResponseDto = new CommentResponseDto(1L,1L,1L,"좋습니다~!","최고입니다~","홍길동",LocalDateTime.now(),LocalDateTime.now());
//        List<CommentResponseDto> comments = new ArrayList<>();
//        comments.add(commentResponseDto);
//
//        ContentImage contentImage = new ContentImage(1L,"1.png");
//        List<ContentImage> contentImages = new ArrayList<>();
//        contentImages.add(contentImage);
//
//        UserImage userImage = new UserImage(1L, "2.png");
//        List<UserImage> userImages = new ArrayList<>();
//        userImages.add(userImage);
//
//        // Arrange
//        Content content = new Content();
//        ContentAllResponseDto contentAllResponseDto = new ContentAllResponseDto(1L,1L, 0L, 0L,"오늘의 날씨 소개", "오늘 날씨 너무 좋다", contentImages,Category.자유게시판, LocalDateTime.now(),LocalDateTime.now(),"홍길동",userImages,comments);
//        when(contentService.findContent(anyLong())).thenReturn(content);
//        when(contentService.updateViewCount(any(Content.class))).thenReturn(content);
//        when(contentMapper.contentToContentAllResponse(
//                any(Content.class),
//                eq(commentRepository),
//                eq(contentImageRepository),eq(userImageRepository))).thenReturn(contentAllResponseDto);
//
//        // Act
//        ResponseEntity responseEntity = contentController.getContent(contentAllResponseDto.getContentId());
//
//        // Assert
//        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
//        assertEquals(contentAllResponseDto, responseEntity.getBody());
//        verify(contentService, times(1)).findContent(contentAllResponseDto.getContentId());
//        verify(contentService, times(1)).updateViewCount(content);
//        verify(contentMapper, times(1)).contentToContentAllResponse(content,commentRepository,contentImageRepository,userImageRepository);
//    }
//
//    @Test
//    void getContents() {
//        CommentResponseDto commentResponseDto = new CommentResponseDto(1L,1L,1L,"좋습니다~!","최고입니다~","홍길동",LocalDateTime.now(),LocalDateTime.now());
//        List<CommentResponseDto> comments = new ArrayList<>();
//        comments.add(commentResponseDto);
//
//        ContentImage contentImage = new ContentImage(1L,"1.png");
//        List<ContentImage> contentImages = new ArrayList<>();
//        contentImages.add(contentImage);
//
//        UserImage userImage = new UserImage(1L, "2.png");
//        List<UserImage> userImages = new ArrayList<>();
//        userImages.add(userImage);
//
//        // Arrange
//        Content content = new Content();
//        ContentResponseDto contentResponseDto = new ContentResponseDto(1L,1L, 0L, 0L,"오늘의 날씨 소개", "오늘 날씨 너무 좋다",contentImages,Category.자유게시판, LocalDateTime.now(),LocalDateTime.now());
//
//        List<ContentResponseDto> contentResponseDtoList = new ArrayList<>();
//        contentResponseDtoList.add(contentResponseDto);
//
//        when(contentService.findContent(anyLong())).thenReturn(content);
//        when(contentMapper.contentsToContentsResponse(
//                anyList(),
//                eq(contentImageRepository))).thenReturn(contentResponseDtoList);
//
//        // Act
//        ResponseEntity responseEntity = contentController.getContent(contentResponseDto.getContentId());
//
//        // Assert
//        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
//        assertEquals(contentResponseDto, responseEntity.getBody());
//        verify(contentService, times(1)).findContent(contentResponseDto.getContentId());
//        verify(contentService, times(1)).updateViewCount(content);
//        verify(contentMapper, times(1)).contentToContentAllResponse(content,commentRepository,contentImageRepository,userImageRepository);
//    }
//
//    @Test
//    void getContentsTodayViewRank() {
//    }
//
//    @Test
//    void getContentsWeeklyViewRank() {
//    }
//
//    @Test
//    void getContentsLikeRank() {
//    }
//
//    @Test
//    void getContentsRecentImage() {
//    }
//
//    @Test
//    void getContentFromCategory() {
//    }
//
//    @Test
//    void getContentFromScrap() {
//    }
//
//    @Test
//    void getSearch() {
//    }
//
//    @Test
//    void patchContent() {
//    }
//
//    @Test
//    void deleteContent() {
//    }
//}