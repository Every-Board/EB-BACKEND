package com.java.everyboard.heart.replyHeart.service;

import com.java.everyboard.constant.HeartType;
import com.java.everyboard.exception.BusinessLogicException;
import com.java.everyboard.exception.ExceptionCode;
import com.java.everyboard.reply.entity.Reply;
import com.java.everyboard.reply.repository.ReplyRepository;
import com.java.everyboard.heart.replyHeart.entity.ReplyHeart;
import com.java.everyboard.heart.replyHeart.repository.ReplyHeartRepository;
import com.java.everyboard.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReplyHeartService {
    private final ReplyHeartRepository replyHeartRepository;
    private final ReplyRepository replyRepository;

    public ReplyHeart createReplyHeart(User user, Reply reply){
        ReplyHeart replyHeart;
        int replyHeartStatus =0;
        //좋아요 안했을 경우
        if (isNotAlreadyHeart(user,reply)) {
            replyHeart = new ReplyHeart(user,reply);
            replyHeart.setHeartType(HeartType.ADD);
            replyHeartStatus = 1;
        }else{//좋아요를 했지만 취소했을 경우
            replyHeart = findVerifiedHeart(user,reply);
            switch (replyHeart.getHeartType()){
                case ADD:
                    replyHeart.setHeartType(HeartType.REMOVE);
                    replyHeartStatus = -1;
                    break;
                case REMOVE:
                    replyHeart.setHeartType(HeartType.ADD);
                    replyHeartStatus = 1;
                    break;
                default:
            }
        }
        long replyHeartCount = reply.getReplyHeartCount();
        replyHeartCount+=replyHeartStatus;
        reply.setReplyHeartCount(replyHeartCount);
        replyRepository.save(reply);
        ReplyHeart savedReplyHeart = replyHeartRepository.save(replyHeart);
        return savedReplyHeart;
    }

    public ReplyHeart findVerifiedHeart(User user,Reply reply) {
        Optional<ReplyHeart> optionalHeart = replyHeartRepository.findByUserAndReply(user, reply);
        ReplyHeart replyHeart = optionalHeart.orElseThrow(
                () -> new BusinessLogicException(ExceptionCode.HEART_NOT_FOUND)
        );
        return replyHeart;
    }

    private boolean isNotAlreadyHeart(User user, Reply reply) {
        return replyHeartRepository.findByUserAndReply(user, reply).isEmpty();
    }
}