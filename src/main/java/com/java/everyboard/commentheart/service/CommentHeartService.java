package com.java.everyboard.commentheart.service;

import com.java.everyboard.comment.entity.Comment;
import com.java.everyboard.comment.repository.CommentRepository;
import com.java.everyboard.commentheart.entity.CommentHeart;
import com.java.everyboard.commentheart.repository.CommentHeartRepository;
import com.java.everyboard.constant.HeartType;
import com.java.everyboard.exception.BusinessLogicException;
import com.java.everyboard.exception.ExceptionCode;
import com.java.everyboard.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentHeartService {
    private final CommentHeartRepository commentHeartRepository;
    private final CommentRepository commentRepository;


    public CommentHeart createCommentHeart(User user, Comment comment){
        CommentHeart commentHeart;
        int commentHeartStatus =0;
        //좋아요 안했을 경우
        if (isNotAlreadyHeart(user, comment)) {
            commentHeart = new CommentHeart(user,comment);
            commentHeart.setHeartType(HeartType.ADD);
            commentHeartStatus = 1;
        }else{//좋아요를 했지만 취소했을 경우
            commentHeart = findVerifiedHeart(user,comment);
            switch (commentHeart.getHeartType()){
                case ADD:
                    commentHeart.setHeartType(HeartType.REMOVE);
                    commentHeartStatus = -1;
                    break;
                case REMOVE:
                    commentHeart.setHeartType(HeartType.ADD);
                    commentHeartStatus = 1;
                    break;
                default:
            }
        }
        long commentHeartCount = comment.getCommentHeartCount();
        commentHeartCount+=commentHeartStatus;
        comment.setCommentHeartCount(commentHeartCount);
        commentRepository.save(comment);
        CommentHeart savedCommentHeart = commentHeartRepository.save(commentHeart);
        return savedCommentHeart;
    }

    public CommentHeart findVerifiedHeart(User user,Comment comment) {
        Optional<CommentHeart> optionalHeart = commentHeartRepository.findByUserAndComment(user, comment);
        CommentHeart commentHeart = optionalHeart.orElseThrow(
                () -> new BusinessLogicException(ExceptionCode.HEART_NOT_FOUND)
        );
        return commentHeart;
    }

    private boolean isNotAlreadyHeart(User user, Comment comment) {
        return commentHeartRepository.findByUserAndComment(user, comment).isEmpty();
    }
}
