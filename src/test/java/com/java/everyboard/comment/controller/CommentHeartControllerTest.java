//package com.java.everyboard.comment.controller;
//
//import com.java.everyboard.comment.entity.Comment;
//import com.java.everyboard.commentheart.entity.CommentHeart;
//import com.java.everyboard.commentheart.mapper.CommentHeartMapper;
//import com.java.everyboard.commentheart.repository.CommentHeartRepository;
//import com.java.everyboard.commentheart.service.CommentHeartService;
//import com.java.everyboard.comment.service.CommentService;
//import com.java.everyboard.commentheart.controller.CommentHeartController;
//import com.java.everyboard.commentheart.mapper.CommentHeartMapper;
//import com.java.everyboard.commentheart.dto.CommentHeartResponseDto;
//import com.java.everyboard.constant.HeartType;
//import com.java.everyboard.user.entity.User;
//import com.java.everyboard.user.service.UserService;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.ArgumentMatchers.*;
//import static org.mockito.Mockito.*;
//
//class CommentHeartControllerTest {
//    @Mock
//    private CommentHeartMapper commentHeartMapper;
//
//    @Mock
//    private CommentHeartService commentHeartService;
//
//    @Mock
//    private UserService userService;
//
//    @Mock
//    private CommentService commentService;
//
//    @InjectMocks
//    private CommentHeartController commentHeartController;
//
//    @BeforeEach
//    void setUp() {
//        MockitoAnnotations.openMocks(this);
//    }
//
//    @Test
//    void postHeart_ValidInput_ReturnsCreated() {
//        // Arrange
//        Long userId = 1L;
//        Long commentId = 2L;
//        User user = new User();
//        Comment comment = new Comment();
//        CommentHeart commentHeart = new CommentHeart();
//        CommentHeartResponseDto responseDto = new CommentHeartResponseDto(1L,1L,2L,1L, "ADD");
//        when(userService.findUser(anyLong())).thenReturn(user);
//        when(userService.getLoginUser()).thenReturn(user);
//        when(commentService.findComment(anyLong())).thenReturn(comment);
//        when(commentHeartService.createCommentHeart(any(User.class), any(Comment.class))).thenReturn(commentHeart);
//        when(commentHeartMapper.commentHeartToCommentHeartResponseDto(any(CommentHeart.class))).thenReturn(responseDto);
//
//        // Act
//        ResponseEntity responseEntity = commentHeartController.postHeart(userId, commentId);
//
//        // Assert
//        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
//        assertEquals(responseDto, responseEntity.getBody());
//        verify(userService, times(1)).findUser(userId);
//        verify(userService, times(1)).getLoginUser();
//        verify(commentService, times(1)).findComment(commentId);
//        verify(commentHeartService, times(1)).createCommentHeart(user, comment);
//        verify(commentHeartMapper, times(1)).commentHeartToCommentHeartResponseDto(commentHeart);
//    }
//}