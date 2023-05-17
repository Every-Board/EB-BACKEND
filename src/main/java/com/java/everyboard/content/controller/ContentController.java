package com.java.everyboard.content.controller;

import com.java.everyboard.constant.Category;
import com.java.everyboard.content.dto.*;
import com.java.everyboard.content.entity.Content;
import com.java.everyboard.content.mapper.ContentMapper;
import com.java.everyboard.content.repository.ContentRepository;
import com.java.everyboard.content.response.MultiResponseDto;
import com.java.everyboard.content.response.SingleResponseDto;
import com.java.everyboard.content.service.ContentService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@Validated
@RequestMapping("/contents")
public class ContentController {
    private final ContentService contentService;
    private final ContentMapper contentMapper;
    private final ContentRepository contentRepository;

    public ContentController(ContentService contentService, ContentMapper contentMapper, ContentRepository contentRepository) {
        this.contentService = contentService;
        this.contentMapper = contentMapper;
        this.contentRepository = contentRepository;
    }

    // 게시글 생성 //
    @PostMapping
    public ResponseEntity postContent(@Valid @RequestBody ContentPostDto requestBody) {
        Content content = contentService.createContent(contentMapper.contentPostDtoToContent(requestBody));
        ContentResponseDto contentResponse = contentMapper.contentToContentResponse(content);

        return new ResponseEntity<>(
                new SingleResponseDto<>(contentResponse) , HttpStatus.CREATED
        );
    }

    // 게시글 단건 조회 //
//    @GetMapping("/{contentId}")
//    public ResponseEntity getContent(@PathVariable("contentId") Long contentId) {
//        Content content = contentService.findContent(contentId);
//        long viewCount = content.getViewCount();
//        content.setViewCount(++viewCount);
//        contentService.updateViewCount(content);
//
//        return contentService.detail(content);
//    }

    // 게시글 전체 조회 //
    @GetMapping
    public ResponseEntity getContents(@RequestParam("page") int page,
                                      @RequestParam("size") int size) {
        Page<Content> pageContents = contentService.findContents(page-1, size);
        List<Content> contents = pageContents.getContent();

        return new ResponseEntity<>(
                new MultiResponseDto<>(
                        contentMapper.contentsToContentResponse(contents),
                        pageContents),
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
}