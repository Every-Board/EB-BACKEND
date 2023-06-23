package com.java.everyboard.commentheart.entity;

import com.java.everyboard.audit.Auditable;
import com.java.everyboard.comment.entity.Comment;
import com.java.everyboard.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
@Entity
public class CommentHeart extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long commentHeartId;

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
        user.addCommentHeart(this);
    }

    public void addComment(Comment comment) {
        this.comment= comment;
        comment.addCommentHeart(this);
    }
}
