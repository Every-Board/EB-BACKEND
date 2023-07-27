package com.java.everyboard.reply.mapper;

import com.java.everyboard.comment.entity.Comment;
import com.java.everyboard.content.entity.Content;
import com.java.everyboard.reply.dto.ReplyPatchDto;
import com.java.everyboard.reply.dto.ReplyPostDto;
import com.java.everyboard.reply.dto.ReplyResponseDto;
import com.java.everyboard.reply.entity.Reply;
import com.java.everyboard.user.entity.User;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ReplyMapper {
    default Reply replyPostDtoToReply(ReplyPostDto requestBody){

        Content content = new Content();
        content.setContentId(requestBody.getContentId());

        Comment comment = new Comment();
        comment.setCommentId(requestBody.getCommentId());

        Reply reply = new Reply();
        reply.setComment(comment);
        reply.setReply(requestBody.getReply());

        return reply;
    }

    default Reply replyPatchDtoToReply(ReplyPatchDto requestBody){

        Content content = new Content();
        content.setContentId(requestBody.getContentId());

        Comment comment = new Comment();
        content.setContentId(requestBody.getContentId());
        comment.setContent(content);
        comment.setCommentId(requestBody.getCommentId());

        Reply reply = new Reply();
        content.setContentId(requestBody.getContentId());
        reply.setContent(content);
        comment.setCommentId(requestBody.getCommentId());
        reply.setComment(comment);
        reply.setReply(requestBody.getReply());

        return reply;
    }

    default ReplyResponseDto replyToReplyResponseDto(Reply reply){
        User user = reply.getUser();
        Content content = reply.getContent();
        Comment comment = reply.getComment();

        return ReplyResponseDto.builder()
                .replyId(reply.getReplyId())
                .userId(user.getUserId())
                .contentId(content.getContentId())
                .commentId(comment.getCommentId())
                .replyHeartCount(reply.getReplyHeartCount())
                .reply(reply.getReply())
                .nickName(user.getNickname())
                .createdAt(reply.getCreatedAt())
                .modifiedAt(reply.getModifiedAt())
                .build();
    }

    List<ReplyResponseDto> repliesToRepliesResponseDto(List<Reply> reply);
}
