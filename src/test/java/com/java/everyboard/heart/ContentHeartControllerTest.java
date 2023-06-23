package com.java.everyboard.heart;

import com.java.everyboard.content.entity.Content;
import com.java.everyboard.content.service.ContentService;
import com.java.everyboard.heart.contentHeart.controller.ContentHeartController;
import com.java.everyboard.heart.contentHeart.dto.ContentHeartResponseDto;
import com.java.everyboard.heart.contentHeart.entity.ContentHeart;
import com.java.everyboard.heart.contentHeart.mapper.ContentHeartMapper;
import com.java.everyboard.heart.contentHeart.service.ContentHeartService;
import com.java.everyboard.user.entity.User;
import com.java.everyboard.user.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

class ContentHeartControllerTest {
    @Mock
    private ContentHeartMapper contentHeartMapper;

    @Mock
    private ContentHeartService contentHeartService;

    @Mock
    private ContentService contentService;
    
    @Mock
    private UserService userService;

    @InjectMocks
    private ContentHeartController contentHeartController;
    
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }
    
    @Test
    void postHeart() {
        Long userId = 1L;
        Long contentId = 1L;
        User user = new User();
        Content content = new Content();
        ContentHeart contentHeart = new ContentHeart();
        ContentHeartResponseDto responseDto = new ContentHeartResponseDto(1L,1L,1L, "ADD");
        when(userService.findUser(anyLong())).thenReturn(user);
        when(userService.getLoginUser()).thenReturn(user);
        when(contentService.findContent(anyLong())).thenReturn(content);
        when(contentHeartService.createContentHeart(any(User.class), any(Content.class))).thenReturn(contentHeart);
        when(contentHeartMapper.contentHeartToContentHeartResponseDto(any(ContentHeart.class))).thenReturn(responseDto);

        // Act
        ResponseEntity responseEntity = contentHeartController.postHeart(userId, contentId);

        // Assert
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals(responseDto, responseEntity.getBody());
        verify(userService, times(1)).findUser(userId);
        verify(userService, times(1)).getLoginUser();
        verify(contentService, times(1)).findContent(contentId);
        verify(contentHeartService, times(1)).createContentHeart(user, content);
        verify(contentHeartMapper, times(1)).contentHeartToContentHeartResponseDto(contentHeart);
    }
}