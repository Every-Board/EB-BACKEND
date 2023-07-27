package com.java.everyboard.comment.controller;

import com.java.everyboard.reply.controller.ReplyController;
import com.java.everyboard.reply.dto.ReplyPatchDto;
import com.java.everyboard.reply.dto.ReplyPostDto;
import com.java.everyboard.reply.dto.ReplyResponseDto;
import com.java.everyboard.reply.entity.Reply;
import com.java.everyboard.reply.mapper.ReplyMapper;
import com.java.everyboard.reply.service.ReplyService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import javax.validation.Validation;
import javax.validation.Validator;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

class ReplyControllerTest {
    @Mock
    private ReplyService replyService;

    @Mock
    private ReplyMapper replyMapper;

    @InjectMocks
    private ReplyController replyController;

    private Validator validator;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Test
    void postReply() {
        ReplyPostDto requestBody = new ReplyPostDto(1L,1L,"덧글 내용");
        Reply reply = new Reply();
        ReplyResponseDto replyResponseDto = new ReplyResponseDto(1L,1L,1L,1L,"게시글제목","덧글 내용",0L, LocalDateTime.now(),LocalDateTime.now());
        when(replyMapper.replyPostDtoToReply(any(ReplyPostDto.class))).thenReturn(reply);
        when(replyService.createReply(any(Reply.class), anyLong(), anyLong())).thenReturn(reply);
        when(replyMapper.replyToReplyResponseDto(any(Reply.class))).thenReturn(replyResponseDto);

        // Act
        ResponseEntity responseEntity = replyController.postReply(requestBody);

        // Assert
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals(replyResponseDto, responseEntity.getBody());
        verify(replyService, times(1)).createReply(reply, requestBody.getContentId(), requestBody.getCommentId());
    }

    @Test
    void patchReply() {
        // Arrange
        ReplyPatchDto requestBody = new ReplyPatchDto(1L,1L,1L,1L,"덧글 내용 수정");
        Long replyId = 1L;
        Reply reply = new Reply();
        ReplyResponseDto replyResponseDto = new ReplyResponseDto(1L,1L,1L,1L,"게시글제목","덧글 내용",0L, LocalDateTime.now(),LocalDateTime.now());
        when(replyMapper.replyPatchDtoToReply(any(ReplyPatchDto.class))).thenReturn(reply);
        when(replyService.updateReply(any(Reply.class), anyLong())).thenReturn(reply);
        when(replyMapper.replyToReplyResponseDto(any(Reply.class))).thenReturn(replyResponseDto);

        // Act
        ResponseEntity responseEntity = replyController.patchReply(requestBody, replyId);

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(replyResponseDto, responseEntity.getBody());
        verify(replyService, times(1)).updateReply(reply, replyId);
    }

    @Test
    void getReply() {
        // Arrange
        Long replyId = 1L;
        Reply reply = new Reply();
        ReplyResponseDto replyResponseDto = new ReplyResponseDto(1L,1L,1L,1L,"게시글제목","덧글 내용",0L,LocalDateTime.now(),LocalDateTime.now());
        when(replyService.findReply(anyLong())).thenReturn(reply);
        when(replyMapper.replyToReplyResponseDto(any(Reply.class))).thenReturn(replyResponseDto);

        // Act
        ResponseEntity responseEntity = replyController.getReply(replyId);

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(replyResponseDto, responseEntity.getBody());
        verify(replyService, times(1)).findReply(replyId);
    }

    @Test
    void getReplies() {
        // Arrange
        List<Reply> replies = new ArrayList<>();
        when(replyService.findReplies()).thenReturn(replies);

        // Act
        ResponseEntity responseEntity = replyController.getReplies();

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        verify(replyService, times(1)).findReplies();
    }


    @Test
    void removeReply() {
        // Arrange
        Long replyId = 1L;

        // Act
        ResponseEntity responseEntity = replyController.removeReply(replyId);

        // Assert
        assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
        verify(replyService, times(1)).deleteReply(replyId);
    }
}