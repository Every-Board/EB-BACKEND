package com.java.everyboard.content.controller;

import com.java.everyboard.awsS3.AwsS3Service;
import com.java.everyboard.comment.dto.CommentResponseDto;
import com.java.everyboard.comment.repository.CommentRepository;
import com.java.everyboard.content.dto.*;
import com.java.everyboard.content.entity.Content;
import com.java.everyboard.constant.Category;
import com.java.everyboard.content.entity.ContentImage;
import com.java.everyboard.content.mapper.ContentMapper;
import com.java.everyboard.content.repository.ContentImageRepository;
import com.java.everyboard.content.repository.ContentRepository;
import com.java.everyboard.content.service.ContentService;
import com.java.everyboard.response.SingleResponseDto;
import com.java.everyboard.user.entity.User;
import com.java.everyboard.user.entity.UserImage;
import com.java.everyboard.user.repository.UserImageRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
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

import static org.mockito.ArgumentMatchers.any;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ContentControllerTest {
    @Mock
    private ContentService contentService;


    @Mock
    private ContentMapper contentMapper;

    @Mock
    private ContentImageRepository contentImageRepository;

    @Mock
    private UserImageRepository userImageRepository;

    @Mock
    private CommentRepository commentRepository;

    @Mock
    private ContentRepository contentRepository;

    @Mock
    private AwsS3Service awsS3Service;

    @InjectMocks
    private ContentController contentController;

    private Validator validator;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @DisplayName("POST 게시글 등록")
    @Test
    void postContent() throws Exception {
        List<MultipartFile> images = new ArrayList<>();
        MockMultipartFile image1 = new MockMultipartFile("image", "1.png", "image/png", "test image".getBytes());
        images.add(image1);
        List<String> imgPaths = awsS3Service.uploadFile(images);

        ContentImage contentImage = new ContentImage(1L,"1.png");
        List<ContentImage> contentImages = new ArrayList<>();
        contentImages.add(contentImage);

        ContentPostDto contentPostDto = new ContentPostDto(1L, "오늘의 날씨 소개","오늘 날씨 너무 좋다", Category.자유게시판, contentImages);
        Content content = new Content();
        ContentResponseDto contentResponseDto = new ContentResponseDto(1L,1L, 0L, 0L,"오늘의 날씨 소개", "오늘 날씨 너무 좋다", contentImages,Category.자유게시판, LocalDateTime.now(),LocalDateTime.now());

        SingleResponseDto sing = new SingleResponseDto(contentResponseDto);

        // Arrange
        when(contentMapper.contentPostDtoToContent(any(ContentPostDto.class))).thenReturn(content);
        when(contentService.createContent(any(Content.class), anyList())).thenReturn(content);
        when(contentMapper.contentToContentResponse(any(Content.class),eq(contentImageRepository))).thenReturn(contentResponseDto);

        // Act
        ResponseEntity responseEntity = contentController.postContent(contentPostDto,images);

        // Assert
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals(sing.toString(), responseEntity.getBody().toString());
        verify(contentMapper, times(1)).contentPostDtoToContent(contentPostDto);
        verify(contentService, times(1)).createContent(content, imgPaths);
        verify(contentMapper, times(1)).contentToContentResponse(content,contentImageRepository);
    }

    @Test
    void getContent() {
        CommentResponseDto commentResponseDto = new CommentResponseDto(1L,1L,1L,"좋습니다~!","최고입니다~","홍길동",LocalDateTime.now(),LocalDateTime.now());
        List<CommentResponseDto> comments = new ArrayList<>();
        comments.add(commentResponseDto);

        ContentImage contentImage = new ContentImage(1L,"1.png");
        List<ContentImage> contentImages = new ArrayList<>();
        contentImages.add(contentImage);

        UserImage userImage = new UserImage(1L, "2.png");
        List<UserImage> userImages = new ArrayList<>();
        userImages.add(userImage);

        // Arrange
        Content content = new Content();
        ContentAllResponseDto contentAllResponseDto = new ContentAllResponseDto(1L,1L, 0L, 0L,"오늘의 날씨 소개", "오늘 날씨 너무 좋다", contentImages,Category.자유게시판, LocalDateTime.now(),LocalDateTime.now(),"홍길동",userImages,comments);
        when(contentService.findContent(anyLong())).thenReturn(content);
        when(contentService.updateViewCount(any(Content.class))).thenReturn(content);
        when(contentMapper.contentToContentAllResponse(
                any(Content.class),
                eq(commentRepository),
                eq(contentImageRepository),eq(userImageRepository))).thenReturn(contentAllResponseDto);

        // Act
        ResponseEntity responseEntity = contentController.getContent(contentAllResponseDto.getContentId());

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(contentAllResponseDto, responseEntity.getBody());
        verify(contentService, times(1)).findContent(contentAllResponseDto.getContentId());
        verify(contentService, times(1)).updateViewCount(content);
        verify(contentMapper, times(1)).contentToContentAllResponse(content,commentRepository,contentImageRepository,userImageRepository);
    }

    @Test
    void getContents() {
        int size = 100;
        CommentResponseDto commentResponseDto = new CommentResponseDto(1L,1L,1L,"좋습니다~!","최고입니다~","홍길동",LocalDateTime.now(),LocalDateTime.now());
        List<CommentResponseDto> comments = new ArrayList<>();
        comments.add(commentResponseDto);

        ContentImage contentImage = new ContentImage(1L,"1.png");
        List<ContentImage> contentImages = new ArrayList<>();
        contentImages.add(contentImage);

        UserImage userImage = new UserImage(1L, "2.png");
        List<UserImage> userImages = new ArrayList<>();
        userImages.add(userImage);

        Content content = new Content();
        ContentResponseDto contentResponseDto = new ContentResponseDto(1L,1L, 0L, 0L,"오늘의 날씨 소개", "오늘 날씨 너무 좋다",contentImages,Category.자유게시판, LocalDateTime.now(),LocalDateTime.now());
        List<Content> contents = new ArrayList<>();
        contents.add(content);

        List<ContentResponseDto> contentResponseDtoList = new ArrayList<>();
        contentResponseDtoList.add(contentResponseDto);

        ContentsListDto contentsListDto = new ContentsListDto(contentResponseDtoList);

        // Arrange
        when(contentMapper.contentsToContentResponse(anyList(), eq(contentImageRepository))).thenReturn(contentsListDto);
        when(contentService.findContents()).thenReturn(contents);

        // Act
        ResponseEntity responseEntity = contentController.getContents(size);

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(contentsListDto, responseEntity.getBody());
        verify(contentService, times(1)).findContents();
    }

    @Test
    void getContentsTodayViewRank() {
        Content content = new Content();
        List<Content> contents = new ArrayList<>();
        contents.add(content);

        HomepageContentResponseDto homepageContentResponseDto = new HomepageContentResponseDto(1L, "오늘의 날씨 소개");
        List<HomepageContentResponseDto> homepageContentResponseDtos = new ArrayList<>();
        homepageContentResponseDtos.add(homepageContentResponseDto);

        // Arrange
        when(contentMapper.contentsToHomepageContentResponseDto(anyList())).thenReturn(homepageContentResponseDtos);
        when(contentService.findContentsTodayViewRank()).thenReturn(contents);

        // Act
        ResponseEntity responseEntity = contentController.getContentsTodayViewRank();

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(homepageContentResponseDtos, responseEntity.getBody());
        verify(contentService, times(1)).findContentsTodayViewRank();
    }
    @Test
    void getContentsWeeklyViewRank() {
        Content content = new Content();
        List<Content> contents = new ArrayList<>();
        contents.add(content);

        HomepageContentResponseDto homepageContentResponseDto = new HomepageContentResponseDto(1L, "오늘의 날씨 소개");
        List<HomepageContentResponseDto> homepageContentResponseDtos = new ArrayList<>();
        homepageContentResponseDtos.add(homepageContentResponseDto);

        // Arrange
        when(contentMapper.contentsToHomepageContentResponseDto(anyList())).thenReturn(homepageContentResponseDtos);
        when(contentService.findContentsWeeklyViewRank()).thenReturn(contents);

        // Act
        ResponseEntity responseEntity = contentController.getContentsWeeklyViewRank();

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(homepageContentResponseDtos, responseEntity.getBody());
        verify(contentService, times(1)).findContentsWeeklyViewRank();
    }

    @Test
    void getContentsLikeRank() {
        Content content = new Content();
        List<Content> contents = new ArrayList<>();
        contents.add(content);

        HomepageContentResponseDto homepageContentResponseDto = new HomepageContentResponseDto(1L, "오늘의 날씨 소개");
        List<HomepageContentResponseDto> homepageContentResponseDtos = new ArrayList<>();
        homepageContentResponseDtos.add(homepageContentResponseDto);

        // Arrange
        when(contentMapper.contentsToHomepageContentResponseDto(anyList())).thenReturn(homepageContentResponseDtos);
        when(contentService.findContentsLikeRank()).thenReturn(contents);

        // Act
        ResponseEntity responseEntity = contentController.getContentsLikeRank();

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(homepageContentResponseDtos, responseEntity.getBody());
        verify(contentService, times(1)).findContentsLikeRank();
    }

    @Test
    void getContentsRecentImage() {
        Content content = new Content();
        List<Content> contents = new ArrayList<>();
        contents.add(content);

        ContentImage contentImage = new ContentImage(1L,"1.png");
        List<ContentImage> contentImages = new ArrayList<>();
        contentImages.add(contentImage);

        HomepageContentImageResponseDto homepageContentImageResponseDto = new HomepageContentImageResponseDto(1L, "오늘의 날씨 소개", "날씨 너무 좋다", 0L, contentImages);
        List<HomepageContentImageResponseDto> homepageContentImageResponseDtos = new ArrayList<>();
        homepageContentImageResponseDtos.add(homepageContentImageResponseDto);

        // Arrange
        when(contentMapper.contentsToHomepageContentImageResponseDto(anyList(),eq(contentImageRepository))).thenReturn(homepageContentImageResponseDtos);
        when(contentService.findContentsRecentImage()).thenReturn(contents);

        // Act
        ResponseEntity responseEntity = contentController.getContentsRecentImage();

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(homepageContentImageResponseDtos, responseEntity.getBody());
        verify(contentService, times(1)).findContentsRecentImage();
    }

    @Test
    void getContentFromCategory() {
        Category category = Category.자유게시판;

        ContentImage contentImage = new ContentImage(1L,"1.png");
        List<ContentImage> contentImages = new ArrayList<>();
        contentImages.add(contentImage);

        List<CategoryResponseDto> categoryResponseDtos = new ArrayList<>();
        CategoryResponseDto categoryResponseDto = new CategoryResponseDto(1L,1L,"홍길동",0L,0L,"오늘의 날씨 소개","날씨 너무 좋다",contentImages,Category.자유게시판,LocalDateTime.now(),LocalDateTime.now());
        categoryResponseDtos.add(categoryResponseDto);

        CategoryContentsResponseDto categoryContentsResponseDto = new CategoryContentsResponseDto(Category.자유게시판, categoryResponseDtos);

        SingleResponseDto sing = new SingleResponseDto(categoryContentsResponseDto);
        // Arrange
        when(contentMapper.categoryContentsResponseDto(any(Category.class),eq(contentRepository),eq(contentImageRepository))).thenReturn(categoryContentsResponseDto);

        // Act
        ResponseEntity responseEntity = contentController.getContentFromCategory(category);

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(sing.toString(), responseEntity.getBody().toString());
    }

    @Test
    void getContentFromScrap() {
        Long userId = 1L;
        User user = new User();

        Content content = new Content();
        List<Content> contents = new ArrayList<>();
        contents.add(content);

        ContentImage contentImage = new ContentImage(1L,"1.png");
        List<ContentImage> contentImages = new ArrayList<>();
        contentImages.add(contentImage);

        ScrapResponseDto response = new ScrapResponseDto(1L,1L,"홍길동",0L,0L,"오늘 날씨 정말 좋다","오늘 여행가기 딱좋음",contentImages,LocalDateTime.now(),LocalDateTime.now());
        List<ScrapResponseDto> scrapResponseDtos = new ArrayList<>();
        scrapResponseDtos.add(response);

        List<ScrapListDto> scrapListDtos = new ArrayList<>();
        ScrapListDto scrapListDto = new ScrapListDto(scrapResponseDtos);
        scrapListDtos.add(scrapListDto);

        SingleResponseDto sing = new SingleResponseDto(scrapListDto);

        // Arrange
        when(contentService.findVerifiedUser(anyLong())).thenReturn(user);
        when(contentMapper.scrapResponseDto(any(User.class),eq(contentRepository),eq(contentImageRepository))).thenReturn(scrapListDto);

        // Act
        ResponseEntity responseEntity = contentController.getContentFromScrap(userId);

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(sing.toString(), responseEntity.getBody().toString());
    }

    @Test
    void getSearch() {
        String keyword = "오늘";

        Content content = new Content();
        List<Content> contents = new ArrayList<>();
        contents.add(content);

        ContentImage contentImage = new ContentImage(1L,"1.png");
        List<ContentImage> contentImages = new ArrayList<>();
        contentImages.add(contentImage);

        ContentResponseDto contentResponseDto = new ContentResponseDto(1L,1L,0L,0L,"오늘 날씨 최고다","오늘 날씨 정말로 좋다!!", contentImages,Category.자유게시판,LocalDateTime.now(),LocalDateTime.now());
        List<ContentResponseDto> contentResponseDtos = new ArrayList<>();
        contentResponseDtos.add(contentResponseDto);

        ContentsListDto contentsListDto = new ContentsListDto(contentResponseDtos);


        // Arrange
        when(contentService.findAllSearch(anyString())).thenReturn(contents);
        when(contentMapper.contentsToContentResponse(anyList(),eq(contentImageRepository))).thenReturn(contentsListDto);

        // Act
        ResponseEntity responseEntity = contentController.getSearch(keyword);

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(contentsListDto, responseEntity.getBody());
    }

    @Test
    void patchContent() {
        ContentImage contentImage = new ContentImage(1L,"1.png");
        List<ContentImage> contentImages = new ArrayList<>();
        contentImages.add(contentImage);

        ContentPatchDto requestBody = new ContentPatchDto(1L,"오늘 날씨 최고","수영장 가고 싶다", contentImages,Category.자유게시판);
        Long contentId = 1L;
        Content content = new Content();
        ContentResponseDto contentResponseDto = new ContentResponseDto(1L,1L,1L,0L,"오늘 날씨 짱","덧글 내용",contentImages,Category.자유게시판, LocalDateTime.now(),LocalDateTime.now());

        when(contentMapper.contentPatchDtoToContent(any(ContentPatchDto.class))).thenReturn(content);
        when(contentService.updateContent(any(Content.class))).thenReturn(content);
        when(contentMapper.contentToContentResponse(any(Content.class),eq(contentImageRepository))).thenReturn(contentResponseDto);

        // Act
        ResponseEntity responseEntity = contentController.patchContent(requestBody, contentId);

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(contentResponseDto, responseEntity.getBody());
        verify(contentService, times(1)).updateContent(content);
    }

    @Test
    void deleteContent() {
        // Arrange
        Long contentId = 1L;

        // Act
        ResponseEntity responseEntity = contentController.deleteContent(contentId);

        // Assert
        assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
        verify(contentService, times(1)).deleteContent(contentId);
    }
}