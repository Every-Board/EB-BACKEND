package com.java.everyboard.replyHeart.controller;

import com.java.everyboard.exception.BusinessLogicException;
import com.java.everyboard.exception.ExceptionCode;
import com.java.everyboard.reply.entity.Reply;
import com.java.everyboard.reply.service.ReplyService;
import com.java.everyboard.replyHeart.dto.ReplyHeartResponseDto;
import com.java.everyboard.replyHeart.entity.ReplyHeart;
import com.java.everyboard.replyHeart.mapper.ReplyHeartMapper;
import com.java.everyboard.replyHeart.repository.ReplyHeartRepository;
import com.java.everyboard.replyHeart.service.ReplyHeartService;
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
public class ReplyHeartController {
    private final ReplyHeartMapper replyHeartMapper;
    private final ReplyHeartService replyHeartService;
    private final ReplyHeartRepository replyHeartRepository;
    private final ReplyService replyService;
    private final UserService userService;

    // 좋아요 등록 //
    @PostMapping("/{userId}/{contentId}/{commentId}/{replyId}/replyhearts")
    public ResponseEntity postHeart(
            @PathVariable("userId") @Positive Long userId,
            @PathVariable("replyId") @Positive Long replyId) {
        User user = userService.findUser(userId);

        if(userService.getLoginUser().getUserId() != user.getUserId())
            throw new BusinessLogicException(ExceptionCode.UNAUTHORIZED);
        Reply reply = replyService.findReply(replyId);
        ReplyHeart replyHeart = replyHeartService.createReplyHeart(user,reply);
        ReplyHeartResponseDto response = replyHeartMapper.replyHeartToReplyHeartResponseDto(replyHeart);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}
