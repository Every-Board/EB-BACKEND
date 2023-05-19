package com.java.everyboard.commentheart.controller;

import com.java.everyboard.comment.entity.Comment;
import com.java.everyboard.comment.service.CommentService;
import com.java.everyboard.commentheart.dto.CommentHeartResponseDto;
import com.java.everyboard.commentheart.mapper.CommentHeartMapper;
import com.java.everyboard.commentheart.repository.CommentHeartRepository;
import com.java.everyboard.commentheart.service.CommentHeartService;
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
        private final CommentHeartMapper heartMapper;
        private final CommentHeartService heartService;
        private final UserService userService;
        private final CommentService contentService;
        private final CommentHeartRepository heartRepository;

        // 좋아요 등록 //
        @PostMapping("/{userId}/{commentId}/hearts")
        public ResponseEntity postHeart(
                @PathVariable("userId") @Positive Long userId,
                @PathVariable("commentId") @Positive Long commentId) {
            User user = userService.findUser(userId);

            if(userService.getLoginMember().getUserId() != user.getUserId())
                throw new BusinessLogicException(ExceptionCode.UNAUTHORIZED);
            Comment comment = contentService.findComment(commentId);
            Heart heart = heartService.createHeart(user,comment);
            CommentHeartResponseDto response = heartMapper.heartToHeartResponseDto(heart);

            return new ResponseEntity<>(response, HttpStatus.CREATED);
        }
}
