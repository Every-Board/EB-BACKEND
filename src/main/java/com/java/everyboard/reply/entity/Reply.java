package com.java.everyboard.reply.entity;

import com.java.everyboard.audit.Auditable;
import com.java.everyboard.comment.entity.Comment;
import com.java.everyboard.content.entity.Content;
import com.java.everyboard.replyHeart.entity.ReplyHeart;
import com.java.everyboard.user.entity.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "REPLIES")
public class Reply extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long replyId;

    @Column(nullable = false)
    private String reply;

    @Column(nullable = false)
    private long replyHeartCount = 0;

    // 연관 관계 //
    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private User user;

    @ManyToOne
    @JoinColumn(name = "CONTENT_ID")
    private Content content;

    @ManyToOne
    @JoinColumn(name = "COMMENT_ID")
    private Comment comment;

    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
    private List<ReplyHeart> replyHearts = new ArrayList<>();

    // 생성자
    public Reply(String reply) {
        this.reply = reply;
    }

    public void setUser(User user) {
        this.user = user;
        if (!this.user.getReplies().contains(this)) {
            this.user.getReplies().add(this);
        }
    }

    public void setComment(Comment comment) {
        this.comment = comment;
        if (!this.comment.getReplies().contains(this)) {
            this.comment.getReplies().add(this);
        }
    }

    // 연관 관계 메서드 //
    public void addReplyHeart(ReplyHeart replyHeart) { replyHearts.add(replyHeart); }
}
