package com.java.everyboard.heart;

import com.java.everyboard.reply.entity.Reply;
import com.java.everyboard.reply.service.ReplyService;
import com.java.everyboard.heart.replyHeart.controller.ReplyHeartController;
import com.java.everyboard.heart.replyHeart.dto.ReplyHeartResponseDto;
import com.java.everyboard.heart.replyHeart.entity.ReplyHeart;
import com.java.everyboard.heart.replyHeart.mapper.ReplyHeartMapper;
import com.java.everyboard.heart.replyHeart.service.ReplyHeartService;
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

class ReplyHeartControllerTest {
    @Mock
    private ReplyHeartMapper replyHeartMapper;

    @Mock
    private ReplyHeartService replyHeartService;

    @Mock
    private ReplyService replyService;

    @Mock
    private UserService userService;

    @InjectMocks
    private ReplyHeartController replyHeartController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void postHeart() {
        Long userId = 1L;
        Long replyId = 1L;
        User user = new User();
        Reply reply = new Reply();
        ReplyHeart replyHeart = new ReplyHeart();
        ReplyHeartResponseDto responseDto = new ReplyHeartResponseDto(1L,1L,1L, "ADD");
        when(userService.findUser(anyLong())).thenReturn(user);
        when(userService.getLoginUser()).thenReturn(user);
        when(replyService.findReply(anyLong())).thenReturn(reply);
        when(replyHeartService.createReplyHeart(any(User.class), any(Reply.class))).thenReturn(replyHeart);
        when(replyHeartMapper.replyHeartToReplyHeartResponseDto(any(ReplyHeart.class))).thenReturn(responseDto);

        // Act
        ResponseEntity responseEntity = replyHeartController.postHeart(userId, replyId);

        // Assert
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals(responseDto, responseEntity.getBody());
        verify(userService, times(1)).findUser(userId);
        verify(userService, times(1)).getLoginUser();
        verify(replyService, times(1)).findReply(replyId);
        verify(replyHeartService, times(1)).createReplyHeart(user, reply);
        verify(replyHeartMapper, times(1)).replyHeartToReplyHeartResponseDto(replyHeart);
    }
}