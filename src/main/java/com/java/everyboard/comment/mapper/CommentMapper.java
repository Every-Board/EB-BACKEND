package com.java.everyboard.comment.mapper;

import com.java.everyboard.comment.dto.CommentPatchDto;
import com.java.everyboard.comment.dto.CommentPostDto;
import com.java.everyboard.comment.dto.CommentResponseDto;
import com.java.everyboard.comment.entity.Comment;
import com.java.everyboard.content.entity.Content;
import com.java.everyboard.user.entity.User;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CommentMapper {
    default Comment commentPostDtoToComment(CommentPostDto requestBody)
    {
        // content id, comment id 받아오기 + body 받아오기
        Content content = new Content();
        content.setContentId(requestBody.getContentId());

        Comment comment = new Comment();
        comment.setContent(content);
        comment.setComment(requestBody.getComment());

        return comment;
    }

    default Comment commentPatchDtoToComment(CommentPatchDto requestBody)
    {
        // content id, comment id 받아오기 + body 받아오기
        Content content = new Content();
        content.setContentId(requestBody.getContentId());

        Comment comment = new Comment();
        content.setContentId(requestBody.getContentId());
        comment.setContent(content);
        comment.setComment(requestBody.getComment());

        return comment;
    }
    default CommentResponseDto commentToCommentResponseDto(Comment comment)
    {
        User user = comment.getUser();
        Content content = comment.getContent();

        return CommentResponseDto.builder()
                .commentId(comment.getCommentId())
                .userId(user.getUserId())
                .contentId(content.getContentId())
                .title(content.getTitle())
                .comment(comment.getComment())
                .nickName(user.getNickname())
                .createdAt(comment.getCreatedAt())
                .modifiedAt(comment.getModifiedAt())
                .build();
    }
    List<CommentResponseDto> commentsToCommentResponseDtos(List<Comment> comment);
}
