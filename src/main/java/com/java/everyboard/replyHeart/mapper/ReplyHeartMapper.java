package com.java.everyboard.replyHeart.mapper;

import com.java.everyboard.reply.entity.Reply;
import com.java.everyboard.replyHeart.dto.ReplyHeartResponseDto;
import com.java.everyboard.replyHeart.entity.ReplyHeart;
import com.java.everyboard.user.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ReplyHeartMapper {
    default ReplyHeartResponseDto replyHeartToReplyHeartResponseDto(ReplyHeart replyHeart){
        User user = replyHeart.getUser();
        Reply reply = replyHeart.getReply();

        return ReplyHeartResponseDto.builder()
                .userId(user.getUserId())
                .replyHeartId(replyHeart.getReplyHeartId())
                .replyId(reply.getReplyId())
                .heartType(replyHeart.getHeartType().toString())
                .build();
    }
}