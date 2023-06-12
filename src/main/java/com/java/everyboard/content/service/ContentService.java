package com.java.everyboard.content.service;

import com.java.everyboard.awsS3.AwsS3Service;
import com.java.everyboard.comment.repository.CommentRepository;
import com.java.everyboard.content.entity.Content;
import com.java.everyboard.content.entity.ContentImage;
import com.java.everyboard.content.mapper.ContentMapper;
import com.java.everyboard.content.repository.ContentImageRepository;
import com.java.everyboard.content.repository.ContentRepository;
import com.java.everyboard.exception.BusinessLogicException;
import com.java.everyboard.exception.ExceptionCode;
import com.java.everyboard.user.entity.User;
import com.java.everyboard.user.repository.UserRepository;
import com.java.everyboard.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;


@Service
@Transactional
@RequiredArgsConstructor
public class ContentService {
    private final UserRepository userRepository;
    private final UserService userService;
    private final ContentRepository contentRepository;
    private final ContentMapper contentMapper;
    private final CommentRepository commentRepository;
    private final AwsS3Service awsS3Service;
    private final ContentImageRepository contentImageRepository;

    // 게시글 생성 //
    public Content createContent(Content content, List<String> imgPaths) {
        blankCheck(imgPaths);
        System.out.println("로그인한 user: "+userService.getLoginUser());
        content.setUser(userService.getLoginUser());

        contentRepository.save(content);

        List<String> fileNameList = new ArrayList<>();
        for (String contentImgUrl : imgPaths) {
            ContentImage img = new ContentImage(content.getContentId(),contentImgUrl);
            img.setContentId(content.getContentId());
            contentImageRepository.save(img);
            fileNameList.add(img.getContentImgUrl());
        }

        return content;
    }

    // 게시글 수정 //
    public Content updateContent(Content content) {
        Content findContent = findVerifiedContent(content.getContentId());

        User writer = userService.findUser(findContent.getUser().getUserId()); // 작성자 찾기
        if(userService.getLoginUser().getUserId() != writer.getUserId()) // 작성자와 로그인한 사람이 다를 경우
            throw new BusinessLogicException(ExceptionCode.UNAUTHORIZED); //예외 발생(권한 없음)

        Optional.ofNullable(content.getTitle())
                .ifPresent(findContent::setTitle);

        Optional.ofNullable(content.getContent())
                .ifPresent(findContent::setContent);

        Optional.ofNullable(content.getCategory())
                .ifPresent(findContent::setCategory);

        return contentRepository.save(findContent);
    }

    // 게시글 단건 조회 //
    public Content findContent(Long contentId) {
        return findVerifiedContent(contentId);
    }

    // 게시글 전체 조회 //
    public List<Content> findContents() {
        return contentRepository.findAll();
    }

    // 게시글 조회수 상위 조회 //
    // today(현재시간-24 ~ 현재시간)
    public List<Content> findContentsTodayViewRank() {
        return contentRepository.findContentsTodayViewRank();
    }

    // weekly(현재시간 -일주일 ~ 현재시간)
    public List<Content> findContentsWeeklyViewRank() {
        return contentRepository.findContentsWeeklyViewRank();
    }

    // 좋아요 상위
    public List<Content> findContentsLikeRank() {
        return contentRepository.findContentsLikeRank();
    }

    // 홈페이지 최신 이미지
    public List<Content> findContentsRecentImage() {
        return contentRepository.findContentsRecentImage();
    }

    // 검색 기능 //
    public List<Content> findAllSearch(String keyword){
        return contentRepository.findAllSearch(keyword);
    }

    // 게시글 삭제 //
    public void deleteContent(Long contentId) {
        Content findContent = findVerifiedContent(contentId);

        User writer = userService.findUser(findContent.getUser().getUserId()); // 작성자 찾기
        if(userService.getLoginUser().getUserId() != writer.getUserId()) // 작성자와 로그인한 사람이 다를 경우
            throw new BusinessLogicException(ExceptionCode.UNAUTHORIZED); //예외 발생(권한 없음)
        contentRepository.delete(findContent);

        List<ContentImage> contentImagesList = contentImageRepository.findByContentId(contentId);
        for(ContentImage contentImage: contentImagesList){
            awsS3Service.deleteFile(contentImage.getContentImgUrl());
        }

        contentImageRepository.deleteAllByContentId(contentId);
    }

    // 유저 검증 로직 //
    public User findVerifiedUser(Long userId) {
        Optional<User> optionalUser = userRepository.findById(userId);
        User findUser =
                optionalUser.orElseThrow(() ->
                        new BusinessLogicException(ExceptionCode.MEMBER_NOT_FOUND));
        return findUser;
    }

    // 게시글 검증 로직 //
    public Content findVerifiedContent(Long contentId) {
        Optional<Content> optionalContent = contentRepository.findByContentId(contentId);

        Content findContent =
                optionalContent.orElseThrow(() ->
                        new BusinessLogicException(ExceptionCode.STACK_NOT_FOUND));

        return findContent;
    }

    public Content updateViewCount(Content content){
        return contentRepository.save(content);
    }

    private void blankCheck(List<String> imgPaths) {
        if(imgPaths == null || imgPaths.isEmpty()){ //.isEmpty()도 되는지 확인해보기
            throw new BusinessLogicException(ExceptionCode.MEMBER_NOT_FOUND);
        }
    }
}