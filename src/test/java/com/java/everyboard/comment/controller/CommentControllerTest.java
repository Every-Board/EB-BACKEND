package com.java.everyboard.comment.controller;

import com.java.everyboard.comment.dto.CommentPatchDto;
import com.java.everyboard.comment.dto.CommentPostDto;
import com.java.everyboard.comment.dto.CommentResponseDto;
import com.java.everyboard.comment.entity.Comment;
import com.java.everyboard.comment.mapper.CommentMapper;
import com.java.everyboard.comment.service.CommentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import javax.validation.Validation;
import javax.validation.Validator;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class CommentControllerTest {
    @Mock
    private CommentService commentService;

    @Mock
    private CommentMapper commentMapper;

    @InjectMocks
    private CommentController commentController;

    private Validator validator;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Test
    void postComment_ValidInput_ReturnsCreated() {
        // Arrange
        CommentPostDto requestBody = new CommentPostDto(1L,"덧글 내용");
        Comment comment = new Comment();
        CommentResponseDto commentResponseDto = new CommentResponseDto(1L,1L,1L,"게시글제목","덧글 내용","홍길동", LocalDateTime.now(),LocalDateTime.now());
        when(commentMapper.commentPostDtoToComment(any(CommentPostDto.class))).thenReturn(comment);
        when(commentService.createComment(any(Comment.class), anyLong())).thenReturn(comment);
        when(commentMapper.commentToCommentResponseDto(any(Comment.class))).thenReturn(commentResponseDto);

        // Act
        ResponseEntity responseEntity = commentController.postComment(requestBody);

        // Assert
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals(commentResponseDto, responseEntity.getBody());
        verify(commentService, times(1)).createComment(comment, requestBody.getContentId());
    }

    @Test
    void patchComment_ValidInput_ReturnsOk() {
        // Arrange
        CommentPatchDto requestBody = new CommentPatchDto(1L,1L,1L,"덧글 내용 수정");
        Long commentId = 1L;
        Comment comment = new Comment();
        CommentResponseDto commentResponseDto = new CommentResponseDto(1L,1L,1L,"게시글제목","덧글 내용","홍길동", LocalDateTime.now(),LocalDateTime.now());
        when(commentMapper.commentPatchDtoToComment(any(CommentPatchDto.class))).thenReturn(comment);
        when(commentService.updateComment(any(Comment.class), anyLong())).thenReturn(comment);
        when(commentMapper.commentToCommentResponseDto(any(Comment.class))).thenReturn(commentResponseDto);

        // Act
        ResponseEntity responseEntity = commentController.patchComment(requestBody, commentId);

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(commentResponseDto, responseEntity.getBody());
        verify(commentService, times(1)).updateComment(comment, commentId);
    }

    @Test
    void getComment_ValidInput_ReturnsOk() {
        // Arrange
        Long commentId = 1L;
        Comment comment = new Comment();
        CommentResponseDto commentResponseDto = new CommentResponseDto(1L,1L,1L,"게시글제목","덧글 내용","홍길동", LocalDateTime.now(),LocalDateTime.now());
        when(commentService.findComment(anyLong())).thenReturn(comment);
        when(commentMapper.commentToCommentResponseDto(any(Comment.class))).thenReturn(commentResponseDto);

        // Act
        ResponseEntity responseEntity = commentController.getComment(commentId);

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(commentResponseDto, responseEntity.getBody());
        verify(commentService, times(1)).findComment(commentId);
    }

    @Test
    void getComments_ValidInput_ReturnsOk() {
        // Arrange
        int page = 1;
        int size = 10;
        List<Comment> comments = new ArrayList<>();
        Page<Comment> pageComments = mock(Page.class);
        when(commentService.findComments(anyInt(), anyInt())).thenReturn(pageComments);
        when(pageComments.getContent()).thenReturn(comments);

        // Act
        ResponseEntity responseEntity = commentController.getComments(page, size);

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        verify(commentService, times(1)).findComments(page - 1, size);
    }

    @Test
    void deleteComment_ValidInput_ReturnsNoContent() {
        // Arrange
        Long commentId = 1L;

        // Act
        ResponseEntity responseEntity = commentController.deleteComment(commentId);

        // Assert
        assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
        verify(commentService, times(1)).deleteComment(commentId);
    }
}
