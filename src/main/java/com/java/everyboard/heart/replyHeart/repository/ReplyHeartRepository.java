package com.java.everyboard.heart.replyHeart.repository;

import com.java.everyboard.heart.replyHeart.entity.ReplyHeart;
import com.java.everyboard.reply.entity.Reply;
import com.java.everyboard.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ReplyHeartRepository extends JpaRepository<ReplyHeart, Long> {
//    Optional<ReplyHeart> findByUserAndCommentAndReply(User user, Comment comment, Reply reply);
    Optional<ReplyHeart> findByUserAndReply(User user, Reply reply);
}
