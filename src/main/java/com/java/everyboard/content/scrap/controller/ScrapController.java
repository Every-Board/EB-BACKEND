package com.java.everyboard.content.scrap.controller;

import com.java.everyboard.content.entity.Content;
import com.java.everyboard.content.scrap.entity.Scrap;
import com.java.everyboard.content.service.ContentService;
import com.java.everyboard.exception.BusinessLogicException;
import com.java.everyboard.exception.ExceptionCode;
import com.java.everyboard.constant.ScrapType;
import com.java.everyboard.content.scrap.dto.ScrapResponseDto;
import com.java.everyboard.content.scrap.mapper.ScrapMapper;
import com.java.everyboard.content.scrap.repository.ScrapRepository;
import com.java.everyboard.content.scrap.service.ScrapService;
import com.java.everyboard.user.entity.User;
import com.java.everyboard.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Positive;

@Slf4j
@Validated
@RestController
@RequestMapping("/{userId}/{contentId}/scraps")
@RequiredArgsConstructor
public class ScrapController {
    private final ScrapMapper scrapMapper;
    private final ScrapService scrapService;
    private final ScrapRepository scrapRepository;
    private final UserService userService;
    private final ContentService contentService;

    // 스크랩한 게시글 포스트 //
    @Transactional
    @PostMapping
    public ResponseEntity postScrap(
            @PathVariable("userId") @Positive Long userId,
            @PathVariable("contentId") @Positive Long contentId) {

        User user = userService.findUser(userId);

        if(userService.getLoginUser().getUserId() != user.getUserId())
            throw new BusinessLogicException(ExceptionCode.UNAUTHORIZED);
        Content content = contentService.findContent(contentId);
        Scrap scrap = scrapService.createScrap(user,content);
        ScrapResponseDto response = scrapMapper.scrapToScrapResponseDto(scrap);

        return new ResponseEntity<>(response,HttpStatus.CREATED);
    }

}