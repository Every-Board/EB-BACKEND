package com.java.everyboard.replyHeart.entity;

import com.java.everyboard.audit.Auditable;
import com.java.everyboard.comment.entity.Comment;
import com.java.everyboard.constant.HeartType;
import com.java.everyboard.reply.entity.Reply;
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
public class ReplyHeart extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long replyHeartId;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private HeartType heartType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "comment_id")
    private Comment comment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reply_id")
    private Reply reply;


    public ReplyHeart(User user, Reply reply) {
        this.user = user;
        this.reply = reply;
    }

    public ReplyHeart(User user) {
        this.user = user;
        user.addReplyHeart(this);
    }

    public ReplyHeart(Comment comment) {
        this.comment = comment;
        comment.addReplyHeart(this);
    }

    public ReplyHeart(Reply reply) {
        this.reply = reply;
        reply.addReplyHeart(this);
    }
}
