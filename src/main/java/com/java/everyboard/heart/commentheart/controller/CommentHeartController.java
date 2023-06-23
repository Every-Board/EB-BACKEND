package com.java.everyboard.heart.commentheart.controller;

import com.java.everyboard.comment.entity.Comment;
import com.java.everyboard.comment.service.CommentService;
import com.java.everyboard.heart.commentheart.dto.CommentHeartResponseDto;
import com.java.everyboard.heart.commentheart.entity.CommentHeart;
import com.java.everyboard.heart.commentheart.mapper.CommentHeartMapper;
import com.java.everyboard.heart.commentheart.repository.CommentHeartRepository;
import com.java.everyboard.heart.commentheart.service.CommentHeartService;
import com.java.everyboard.exception.BusinessLogicException;
import com.java.everyboard.exception.ExceptionCode;
import com.java.everyboard.user.entity.User;
import com.java.everyboard.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.Positive;

@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
public class CommentHeartController {
        private final CommentHeartMapper commentHeartMapper;
        private final CommentHeartService commentHeartService;
        private final UserService userService;
        private final CommentService contentService;
        private final CommentHeartRepository commentHeartRepository;

        // 좋아요 등록 //
        @PostMapping("/{userId}/{contentId}/{commentId}/commenthearts")
        public ResponseEntity postHeart(
                @PathVariable("userId") @Positive Long userId,
                @PathVariable("commentId") @Positive Long commentId) {
            User user = userService.findUser(userId);

            if(userService.getLoginUser().getUserId() != user.getUserId())
                throw new BusinessLogicException(ExceptionCode.UNAUTHORIZED);
            Comment comment = contentService.findComment(commentId);
            CommentHeart commentHeart = commentHeartService.createCommentHeart(user,comment);
            CommentHeartResponseDto response = commentHeartMapper.commentHeartToCommentHeartResponseDto(commentHeart);

            return new ResponseEntity<>(response, HttpStatus.CREATED);
        }
}
