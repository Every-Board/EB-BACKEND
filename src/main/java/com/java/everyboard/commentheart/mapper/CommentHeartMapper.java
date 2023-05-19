package com.java.everyboard.commentheart.mapper;

import com.java.everyboard.comment.entity.Comment;
import com.java.everyboard.commentheart.dto.CommentHeartResponseDto;
import com.java.everyboard.commentheart.entity.CommentHeart;

import java.util.List;

public interface CommentHeartMapper {
    default CommentHeartResponseDto heartToHeartResponseDto(CommentHeart heart) {
        User user = heart.getUser();
        Comment comment = heart.getComment();

        return CommentHeartResponseDto.builder()
                .userId(user.getUserId())
                .heartId(heart.getHeartId())
                .commentId(comment.getCommentId())
                .build();
    }
}
