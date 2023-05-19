package com.java.everyboard.commentheart.entity;

import com.java.everyboard.comment.entity.Comment;
import com.java.everyboard.user.entity.User;
import org.springframework.data.domain.Auditable;

import javax.persistence.*;

public class CommentHeart extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long heartId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "comment_id")
    private Comment comment;

    public CommentHeart(User user, Comment comment) {
        this.user = user;
        this.comment = comment;
    }

    public void addUser(User user) {
        this.user = user;
        user.addHeart(this);
    }

    public void addComment(Comment comment) {
        this.comment= comment;
        comment.addHeart(this);
    }
}
