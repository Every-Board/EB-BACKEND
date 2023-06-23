package com.java.everyboard.commentheart.repository;

import com.java.everyboard.comment.entity.Comment;
import com.java.everyboard.commentheart.entity.CommentHeart;
import com.java.everyboard.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CommentHeartRepository extends JpaRepository<CommentHeart, Long> {
    Optional<CommentHeart> findByUserAndComment(User user, Comment comment);
}