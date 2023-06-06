package com.java.everyboard.comment.entity;

import com.java.everyboard.audit.Auditable;
import com.java.everyboard.commentheart.entity.CommentHeart;
import com.java.everyboard.content.entity.Content;
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
@Table(name = "COMMENTS")
public class Comment extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long commentId;

    @Column(nullable = false)
    private String comment;

    @Column(nullable = false)
    private long commentHeartCount = 0;

    // 연관 관계 //
    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private User user;

    @ManyToOne
    @JoinColumn(name = "CONTENT_ID")
    private Content content;

    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
    private List<CommentHeart> commentHearts = new ArrayList<>();

    //생성자
    public Comment(String comment) {
        this.comment = comment;
    }

    //Comment와 연관관계를 맺을 대상인 User 객체
    public void setUser(User user) {
        this.user = user;
        if (!this.user.getComments().contains(this)) {
            this.user.getComments().add(this);
        }
    }

    //Comment와 연관관계를 맺을 대상인 Post 객체
    public void setContent(Content content) {
        this.content = content;
        if (!this.content.getComments().contains(this)) {
            this.content.getComments().add(this);
        }
    }

    // 연관 관계 메서드 //
    public void addCommentHeart(CommentHeart commentHeart) { commentHearts.add(commentHeart); }
}