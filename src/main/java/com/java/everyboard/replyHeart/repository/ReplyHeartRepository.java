package com.java.everyboard.replyHeart.repository;

import com.java.everyboard.comment.entity.Comment;
import com.java.everyboard.commentheart.entity.CommentHeart;
import com.java.everyboard.reply.entity.Reply;
import com.java.everyboard.replyHeart.entity.ReplyHeart;
import com.java.everyboard.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ReplyHeartRepository extends JpaRepository<ReplyHeart, Long> {
//    Optional<ReplyHeart> findByUserAndCommentAndReply(User user, Comment comment, Reply reply);
    Optional<ReplyHeart> findByUserAndReply(User user, Reply reply);
}
