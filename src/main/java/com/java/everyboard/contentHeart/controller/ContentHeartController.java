package com.java.everyboard.contentHeart.controller;

import com.java.everyboard.content.entity.Content;
import com.java.everyboard.content.service.ContentService;
import com.java.everyboard.constant.HeartType;
import com.java.everyboard.contentHeart.dto.ContentHeartResponseDto;
import com.java.everyboard.contentHeart.entity.ContentHeart;
import com.java.everyboard.contentHeart.mapper.ContentHeartMapper;
import com.java.everyboard.contentHeart.repository.ContentHeartRepository;
import com.java.everyboard.contentHeart.service.ContentHeartService;
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
public class ContentHeartController {
    private final ContentHeartMapper contentHeartMapper;
    private final ContentHeartService contentHeartService;
    private final UserService userService;
    private final ContentService contentService;
    private final ContentHeartRepository contentHeartRepository;

    // 좋아요 등록 //
    @PostMapping("/{userId}/{contentId}/contenthearts")
    public ResponseEntity postHeart(
            @PathVariable("userId") @Positive Long userId,
            @PathVariable("contentId") @Positive Long contentId) {
        User user = userService.findUser(userId);

        if(userService.getLoginUser().getUserId() != user.getUserId())
            throw new BusinessLogicException(ExceptionCode.UNAUTHORIZED);
        Content content = contentService.findContent(contentId);
        ContentHeart contentHeart = contentHeartService.createContentHeart(user,content);
        ContentHeartResponseDto response = contentHeartMapper.contentHeartToContentHeartResponseDto(contentHeart);

        if(contentHeart.getHeartType()== HeartType.REMOVE){
            contentHeartRepository.delete(contentHeart);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(response,HttpStatus.CREATED);
    }

}