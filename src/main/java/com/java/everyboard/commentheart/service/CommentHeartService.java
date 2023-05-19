package com.java.everyboard.commentheart.service;

import com.java.everyboard.comment.repository.CommentRepository;
import com.java.everyboard.commentheart.repository.CommentHeartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentHeartService {
    private final CommentHeartRepository heartRepository;
    private final CommentRepository commentRepository;


    public Heart createHeart(User user, Comment comment){
        Heart heart;
        int heartStatus =0;
        //좋아요 안했을 경우
        if (isNotAlreadyHeart(user, content)) {
            heart = new Heart(user,content);
            heartStatus = 1;
        }else{//좋아요를 했지만 취소했을 경우
            heart = findVerifiedHeart(user,content);
            heartStatus = -1;
        }
        int heartCount = comment.getHeartCount();
        heartCount+=heartStatus;
        comment.setHeartCount(heartCount);
        commentRepository.save(comment);
        Heart SavedHeart = heartRepository.save(heart);
        return SavedHeart;
    }

    public Heart findVerifiedHeart(User user,Comment comment) {
        Optional<Heart> optionalHeart = heartRepository.findByUserAndContent(user, comment);
        Heart heart = optionalHeart.orElseThrow(
                () -> new BusinessLogicException(ExceptionCode.HEART_NOT_FOUND)
        );
        return heart;
    }

    private boolean isNotAlreadyHeart(User user, Comment comment) {
        return heartRepository.findByUserAndComment(user, comment).isEmpty();
    }
}
