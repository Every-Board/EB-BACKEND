package com.java.everyboard.contentHeart.service;

import com.java.everyboard.content.entity.Content;
import com.java.everyboard.content.repository.ContentRepository;
import com.java.everyboard.contentHeart.constant.HeartType;
import com.java.everyboard.contentHeart.entity.ContentHeart;
import com.java.everyboard.contentHeart.repository.ContentHeartRepository;
import com.java.everyboard.exception.BusinessLogicException;
import com.java.everyboard.exception.ExceptionCode;
import com.java.everyboard.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ContentHeartService {
    private final ContentHeartRepository contentHeartRepository;
    private final ContentRepository contentRepository;


    public ContentHeart createContentHeart(User user, Content content){
        ContentHeart contentHeart;
        int contentHeartStatus =0;
        //좋아요 안했을 경우
        if (isNotAlreadyHeart(user, content)) {
            contentHeart = new ContentHeart(user,content);
            contentHeart.setHeartType(HeartType.ADD);
            contentHeartStatus = 1;
        }else{//좋아요를 했지만 취소했을 경우
            contentHeart = findVerifiedHeart(user,content);
            switch(contentHeart.getHeartType()){
                case ADD:
                    contentHeart.setHeartType(HeartType.REMOVE);
                    contentHeartStatus = -1;
                    break;
                case REMOVE:
                    contentHeart.setHeartType(HeartType.ADD);
                    contentHeartStatus = 1;
                    break;
                default:
            }
        }
        long contentHeartCount = content.getContentHeartCount();
        contentHeartCount+=contentHeartStatus;
        content.setContentHeartCount(contentHeartCount);
        contentRepository.save(content);
        ContentHeart savedContentHeart = contentHeartRepository.save(contentHeart);
        return savedContentHeart;
    }

    public ContentHeart findVerifiedHeart(User user,Content content) {
        Optional<ContentHeart> optionalHeart = contentHeartRepository.findByUserAndContent(user, content);
        ContentHeart contentHeart = optionalHeart.orElseThrow(
                () -> new BusinessLogicException(ExceptionCode.MEMBER_NOT_FOUND)
        );
        return contentHeart;
    }

    private boolean isNotAlreadyHeart(User user, Content content) {
        return contentHeartRepository.findByUserAndContent(user, content).isEmpty();
    }
}