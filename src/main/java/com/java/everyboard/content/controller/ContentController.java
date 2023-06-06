package com.java.everyboard.content.controller;

import com.java.everyboard.AwsS3.AwsS3Service;
import com.java.everyboard.constant.Category;
import com.java.everyboard.content.dto.*;
import com.java.everyboard.content.entity.Content;
import com.java.everyboard.content.mapper.ContentMapper;
import com.java.everyboard.content.repository.ContentRepository;
import com.java.everyboard.exception.BusinessLogicException;
import com.java.everyboard.exception.ErrorResponse;
import com.java.everyboard.exception.ExceptionCode;
import com.java.everyboard.response.SingleResponseDto;
import com.java.everyboard.content.service.ContentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;

@RestController
@Validated
@Slf4j
@RequestMapping("/contents")
@RequiredArgsConstructor
public class ContentController {
    private final ContentService contentService;
    private final ContentMapper contentMapper;
    private final ContentRepository contentRepository;
    private final AwsS3Service awsS3Service;


    // 게시글 생성 //
    @PostMapping
    public ResponseEntity postContent(@Valid @RequestBody ContentPostDto requestBody,
                                      @RequestPart("ContentImgUrl") List<MultipartFile> multipartFiles) {
        if (multipartFiles == null) {
            throw new BusinessLogicException(ExceptionCode.STACK_NOT_FOUND);
        }
        List<String> imgPaths = awsS3Service.uploadFile(multipartFiles);
        log.info("IMG 경로들 : "+ imgPaths);
        Content content = contentService.createContent(contentMapper.contentPostDtoToContent(requestBody),imgPaths);
        ContentResponseDto contentResponse = contentMapper.contentToContentResponse(content);

        return new ResponseEntity<>(
                new SingleResponseDto<>(contentResponse) , HttpStatus.CREATED
        );
    }

    // 게시글 단건 조회 //
    @GetMapping("/{contentId}")
    public ResponseEntity getContent(@PathVariable("contentId") Long contentId) {
        Content content = contentService.findContent(contentId);
        long viewCount = content.getViewCount();
        content.setViewCount(++viewCount);
        contentService.updateViewCount(content);

        return contentService.detail(content);
    }

    // 게시글 전체 조회 //
    @GetMapping
    public ResponseEntity getContents(@RequestParam("size") int size) {
        List<Content> contents = contentService.findContents();

        return new ResponseEntity<>(contentMapper.contentsToContentResponse(contents),
                HttpStatus.OK);
    }


    // 홈페이지 조회수 상위 조회 //
    // today(현재시간-24 ~ 현재시간)
    @GetMapping("/homepage/today")
    public ResponseEntity getContentsTodayViewRank() {
        List<Content> contents = contentService.findContentsTodayViewRank();

        return new ResponseEntity<>(contentMapper.contentsToHomepageContentResponseDto(contents),
                HttpStatus.OK);
    }

    // weekly(현재시간 -일주일 ~ 현재시간)
    @GetMapping("/homepage/weekly")
    public ResponseEntity getContentsWeeklyViewRank() {
        List<Content> contents = contentService.findContentsWeeklyViewRank();

        return new ResponseEntity<>(contentMapper.contentsToHomepageContentResponseDto(contents),
                HttpStatus.OK);
    }

    // 좋아요 상위
    @GetMapping("/homepage/like")
    public ResponseEntity getContentsLikeRank() {
        List<Content> contents = contentService.findContentsLikeRank();

        return new ResponseEntity<>(contentMapper.contentsToHomepageContentResponseDto(contents),
                HttpStatus.OK);
    }

    // 홈페이지 최신 이미지
    @GetMapping("/homepage/image")
    public ResponseEntity getContentsRecentImage() {
        List<Content> contents = contentService.findContentsRecentImage();

        return new ResponseEntity<>(contentMapper.contentsToHomepageContentImageResponseDto(contents),
                HttpStatus.OK);
    }


    // 게시글 수정 //
    @PatchMapping("/{contentId}")
    public ResponseEntity patchContent(@RequestBody ContentPatchDto requestBody,
                                       @PathVariable("contentId") Long contentId) {
        requestBody.updateId(contentId);
        Content content = contentService.updateContent(
                contentMapper.contentPatchDtoToContent(requestBody));

        ContentResponseDto contentResponse = contentMapper.contentToContentResponse(content);

        return new ResponseEntity<>(contentResponse, HttpStatus.OK);
    }

    // 게시글 삭제 //
    @DeleteMapping("/{contentId}")
    public ResponseEntity deleteContent(@PathVariable("contentId") Long contentId) {
        contentService.deleteContent(contentId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // 카테고리별 컨텐츠 조회
    @GetMapping("/category/{category}")
    public ResponseEntity getContentFromCategory(@PathVariable("category") Category category){
        CategoryContentsResponseDto response = contentMapper.categoryContentsResponseDto(category, contentRepository);
        return new ResponseEntity<>(
                new SingleResponseDto<>(response), HttpStatus.OK
        );
    }

    // 게시글 검색 기능 //
    @GetMapping("/search")
    public ResponseEntity getSearch(@RequestParam(value = "keyword",required = false) String keyword) {
        List<Content> contents = contentService.findAllSearch(keyword);
        return new ResponseEntity<>(contentMapper.contentsToContentResponse(contents),
                HttpStatus.OK);
    }
}
