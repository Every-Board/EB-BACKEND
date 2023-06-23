package com.java.everyboard.reply.repository;

import com.java.everyboard.comment.entity.Comment;
import com.java.everyboard.reply.entity.Reply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReplyRepository extends JpaRepository<Reply,Long> {
    @Query(value = "select * from replies where reply_id = :replyId", nativeQuery = true)
    List<Reply> findAllByReplyId(@Param("replyId") long replyId);

    @Query(value = "select * from replies where user_id = :userId", nativeQuery = true)
    List<Reply> findByUserId(@Param("userId") long userId);
}
