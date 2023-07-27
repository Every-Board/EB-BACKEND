package com.java.everyboard.heart.commentheart.mapper;

import com.java.everyboard.comment.entity.Comment;
import com.java.everyboard.heart.commentheart.dto.CommentHeartResponseDto;
import com.java.everyboard.heart.commentheart.entity.CommentHeart;
import com.java.everyboard.user.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CommentHeartMapper {
    default CommentHeartResponseDto commentHeartToCommentHeartResponseDto(CommentHeart commentHeart) {
        User user = commentHeart.getUser();
        Comment comment = commentHeart.getComment();

        return CommentHeartResponseDto.builder()
                .userId(user.getUserId())
                .commentHeartId(commentHeart.getCommentHeartId())
                .commentId(comment.getCommentId())
                .heartType(commentHeart.getHeartType().toString())
                .build();
    }
}
