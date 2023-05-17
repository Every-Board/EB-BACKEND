package com.java.everyboard.content.service;

import com.java.everyboard.content.dto.ContentAllResponseDto;
import com.java.everyboard.content.entity.Content;
import com.java.everyboard.content.mapper.ContentMapper;
import com.java.everyboard.content.repository.ContentRepository;
import com.java.everyboard.content.response.SingleResponseDto;
import com.java.everyboard.exception.BusinessLogicException;
import com.java.everyboard.exception.ExceptionCode;
import com.java.everyboard.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
public class ContentService {
//    private final UserRepository userRepository;
//    private final UserService userService;
    private final ContentRepository contentRepository;

    private final ContentMapper contentMapper;

//    private final CommentRepository commentRepository;

    public ContentService(ContentRepository contentRepository, ContentMapper contentMapper) {
        this.contentRepository = contentRepository;
        this.contentMapper = contentMapper;
    }

    /*public ContentService(UserRepository userRepository, UserService userService, ContentRepository contentRepository, ContentMapper contentMapper, CommentRepository commentRepository) {
        this.userRepository = userRepository;
        this.userService = userService;
        this.contentRepository = contentRepository;
        this.contentMapper = contentMapper;
        this.commentRepository = commentRepository;
    }*/

    // 게시글 생성 //
    public Content createContent(Content content) {
//        content.setUser(userService.getLoginMember());

        return contentRepository.save(content);
    }

    // 게시글 수정 //
    public Content updateContent(Content content) {
        Content findContent = findVerifiedContent(content.getContentId());

        /*User writer = userService.findVerifiedUser(findContent.getUser().getUserId()); // 작성자 찾기
        if(userService.getLoginMember().getUserId() != writer.getUserId()) // 작성자와 로그인한 사람이 다를 경우
            throw new BusinessLogicException(ExceptionCode.UNAUTHORIZED); //예외 발생(권한 없음)
*/
        Optional.ofNullable(content.getTitle())
                .ifPresent(findContent::setTitle);

        Optional.ofNullable(content.getCategory())
                .ifPresent(findContent::setCategory);

        Optional.ofNullable(content.getTag())
                .ifPresent(findContent::setTag);

        return contentRepository.save(findContent);
    }

    // 게시글 단건 조회 //
    public Content findContent(Long contentId) {
        return findVerifiedContent(contentId);
    }

    // 게시글 전체 조회 //
    public Page<Content> findContents(int page, int size) {
        return contentRepository.findAll(PageRequest.of(page, size,
                Sort.by("contentId").descending()));
    }

    // 게시글 삭제 //
    public void deleteContent(Long contentId) {
        Content findContent = findVerifiedContent(contentId);

//        User writer = userService.findVerifiedUser(findContent.getUser().getUserId()); // 작성자 찾기
//        if(userService.getLoginMember().getUserId() != writer.getUserId()) // 작성자와 로그인한 사람이 다를 경우
//            throw new BusinessLogicException(ExceptionCode.UNAUTHORIZED); //예외 발생(권한 없음)
        contentRepository.delete(findContent);
    }

//    // 유저 검증 로직 //
//    public User findVerifiedUser(Long userId) {
//        Optional<User> optionalUser = userRepository.findById(userId);
//        User findUser =
//                optionalUser.orElseThrow(() ->
//                        new BusinessLogicException(ExceptionCode.MEMBER_NOT_FOUND));
//        return findUser;
//    }

    // 게시글 검증 로직 //
    public Content findVerifiedContent(Long contentId) {
        Optional<Content> optionalContent = contentRepository.findById(contentId);
        Content findContent =
                optionalContent.orElseThrow(() ->
                        new BusinessLogicException(ExceptionCode.STACK_NOT_FOUND));

        return findContent;
    }
    public Content updateViewCount(Content content){
        return contentRepository.save(content);
    }

//    @Transactional
//    public ResponseEntity detail(Content content) {
//
//        ContentAllResponseDto response = contentMapper.contentToContentAllResponse(content, commentRepository);
//
//        return new ResponseEntity<>(
//                new SingleResponseDto<>(response), HttpStatus.OK
//        );
//    }
}