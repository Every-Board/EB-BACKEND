package com.java.everyboard.content.controller;

import com.java.everyboard.content.entity.Content;
import com.java.everyboard.content.scrap.controller.ScrapController;
import com.java.everyboard.content.scrap.dto.ScrapResponseDto;
import com.java.everyboard.content.scrap.entity.Scrap;
import com.java.everyboard.content.scrap.mapper.ScrapMapper;
import com.java.everyboard.content.scrap.repository.ScrapRepository;
import com.java.everyboard.content.scrap.service.ScrapService;
import com.java.everyboard.content.service.ContentService;
import com.java.everyboard.user.entity.User;
import com.java.everyboard.user.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

class ScrapControllerTest {
    @Mock
    private ScrapMapper scrapMapper;

    @Mock
    private ScrapService scrapService;

    @Mock
    private ScrapRepository scrapRepository;

    @Mock
    private ContentService contentService;

    @Mock
    private UserService userService;

    @InjectMocks
    private ScrapController scrapController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void postScrap() {
        // Arrange
        Long userId = 1L;
        Long contentId = 1L;
        User user = new User();
        Content content = new Content();
        Scrap scrap = new Scrap();
        ScrapResponseDto responseDto = new ScrapResponseDto(1L, 1L,1L,"ADD","사람구함","잘생긴사람", LocalDateTime.now(),LocalDateTime.now());
        when(userService.findUser(anyLong())).thenReturn(user);
        when(userService.getLoginUser()).thenReturn(user);
        when(contentService.findContent(anyLong())).thenReturn(content);
        when(scrapService.createScrap(any(User.class), any(Content.class))).thenReturn(scrap);
        when(scrapMapper.scrapToScrapResponseDto(any(Scrap.class))).thenReturn(responseDto);

        // Act
        ResponseEntity responseEntity = scrapController.postScrap(userId, contentId);

        // Assert
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals(responseDto, responseEntity.getBody());
        verify(userService, times(1)).findUser(userId);
        verify(userService, times(1)).getLoginUser();
        verify(contentService, times(1)).findContent(contentId);
        verify(scrapService, times(1)).createScrap(user, content);
        verify(scrapMapper, times(1)).scrapToScrapResponseDto(scrap);
    }
}