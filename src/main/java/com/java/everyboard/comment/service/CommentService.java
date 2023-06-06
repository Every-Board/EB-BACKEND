package com.java.everyboard.comment.service;

import com.java.everyboard.comment.entity.Comment;
import com.java.everyboard.comment.repository.CommentRepository;
import com.java.everyboard.content.entity.Content;
import com.java.everyboard.content.service.ContentService;
import com.java.everyboard.exception.BusinessLogicException;
import com.java.everyboard.exception.ExceptionCode;
import com.java.everyboard.user.entity.User;
import com.java.everyboard.user.repository.UserRepository;
import com.java.everyboard.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class CommentService {
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final ContentService contentService;

    private final UserService userService;

    public Comment createComment(
            Comment comment,
            Long contentId) {
        // 이미 등록된 이메일인지 확인
        Content content = contentService.findContent(contentId);
        User user = userService.getLoginUser();

        comment.setUser(user);
//        comment.setNickname(user.getNickname());
        comment.setContent(content);

        return commentRepository.save(comment);
    }

    // 코멘트 수정
    public Comment updateComment(
            Comment comment,
            Long commentId) {


        Comment findComment = findVerifiedComment(commentId); //ID로 멤버 존재 확인하고 comment 정보 반환
        User writer = userService.findUser(findComment.getUser().getUserId()); // 작성자 찾기
        if (userService.getLoginUser().getUserId() != writer.getUserId()) // 작성자와 로그인한 사람이 다를 경우
            throw new BusinessLogicException(ExceptionCode.UNAUTHORIZED);

        Optional.ofNullable(comment.getComment())
                .ifPresent(findComment::setComment);

        return commentRepository.save(findComment);
    }

    public Comment findComment(long commentId) {
        return findVerifiedComment(commentId);
    }

    public Page<Comment> findComments(int page, int size) {
        return commentRepository.findAll(PageRequest.of(page, size,
                Sort.by("commentId").descending()));
    }

    //코멘트 삭제
    public void deleteComment(long commentId) {
        Comment findComment = findVerifiedComment(commentId);

        User writer = userService.findUser(findComment.getUser().getUserId()); // 작성자 찾기
        if (userService.getLoginUser().getUserId() != writer.getUserId()) // 작성자와 로그인한 사람이 다를 경우
            throw new BusinessLogicException(ExceptionCode.UNAUTHORIZED);

        commentRepository.delete(findComment);
    }

    public Comment findVerifiedComment(long commentId) {
        Optional<Comment> optionalComment = commentRepository.findById(commentId);
        Comment findComment =
                optionalComment.orElseThrow(() ->
                        new BusinessLogicException(ExceptionCode.COMMENT_NOT_FOUND));
        return findComment;
    }
}
