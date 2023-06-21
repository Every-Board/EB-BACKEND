//package com.java.everyboard.content.controller;
//
//import com.google.gson.Gson;
//import com.java.everyboard.AwsS3.AwsS3Service;
//import com.java.everyboard.content.dto.ContentResponseDto;
//import com.java.everyboard.content.entity.Content;
//import com.java.everyboard.constant.Category;
//import com.java.everyboard.content.dto.ContentPostDto;
//import com.java.everyboard.content.entity.ContentImage;
//import com.java.everyboard.content.mapper.ContentMapper;
//import com.java.everyboard.content.repository.ContentImageRepository;
//import com.java.everyboard.content.service.ContentService;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.mock.web.MockMultipartFile;
//import org.springframework.security.test.context.support.WithMockUser;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.web.multipart.MultipartFile;
//
//import javax.transaction.Transactional;
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
//    @Autowired
//    private MockMvc mockMvc;
//
//    @Autowired
//    private Gson gson;
//
//    @MockBean
//    private ContentService contentService;
//
//    @InjectMocks
//    private ContentController contentController;
//
//    @MockBean
//    private ContentMapper contentMapper;
//
//    @MockBean
//    private ContentImageRepository contentImageRepository;
//
//    @MockBean
//    private AwsS3Service awsS3Service;
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
//        ContentImage contentImage = new ContentImage(1L,"1.png");
//        List<ContentImage> contentImages = new ArrayList<>();
//        contentImages.add(contentImage);
//
//        ContentPostDto contentPostDto = new ContentPostDto(1L, "오늘의 날씨 소개","오늘 날씨 너무 좋다", Category.자유게시판, contentImages);
//        Content content = new Content();
//        ContentResponseDto contentResponseDto = new ContentResponseDto(1L,1L, 0L, 0L,"오늘의 날씨 소개", "오늘 날씨 너무 좋다", contentImages,Category.자유게시판, LocalDateTime.now(),LocalDateTime.now());
//
//        when(contentMapper.contentPostDtoToContent(any(ContentPostDto.class))).thenReturn(content);
//        when(contentService.createContent(any(Content.class), anyList())).thenReturn(content);
//        when(contentMapper.contentToContentResponse(any(Content.class),eq(contentImageRepository))).thenReturn(contentResponseDto);
//
//        // Act
//        ResponseEntity responseEntity = contentController.postContent(contentPostDto,images);
//
//        // Assert
//        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
//        assertEquals(contentResponseDto, responseEntity.getBody());
//        verify(contentService, times(1)).createContent(content, imgPaths);
//    }
//
//    @Test
//    void getContent() {
//        ContentImage contentImage = new ContentImage(1L,"1.png");
//        List<ContentImage> contentImages = new ArrayList<>();
//        contentImages.add(contentImage);
//        contentImageRepository.save(contentImage);
//
//        // Arrange
//        Long contentId = 1L;
//        Content content = new Content();
//        ContentResponseDto contentResponseDto = new ContentResponseDto(1L,1L,1L,0L,"게시글제목","덧글 내용",contentImages,Category.자유게시판, LocalDateTime.now(),LocalDateTime.now());
//        when(contentService.findContent(anyLong())).thenReturn(content);
//        when(contentMapper.contentToContentResponse(any(Content.class),eq(contentImageRepository))).thenReturn(contentResponseDto);
//
//        // Act
//        ResponseEntity responseEntity = contentController.getContent(contentId);
//
//        // Assert
//        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
//        assertEquals(contentResponseDto, responseEntity.getBody());
//        verify(contentService, times(1)).findContent(contentId);
//    }
//
//    @Test
//    void getContents() {
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