package com.java.everyboard.heart.contentHeart.entity;

import com.java.everyboard.audit.Auditable;
import com.java.everyboard.content.entity.Content;
import com.java.everyboard.constant.HeartType;
import com.java.everyboard.user.entity.User;
import lombok.*;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
@Entity
public class ContentHeart extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long contentHeartId;
    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private HeartType heartType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "content_id")
    private Content content;

    public ContentHeart(User user, Content content) {
        this.user = user;
        this.content = content;
    }

    public void addUser(User user) {
        this.user = user;
        user.addContentHeart(this);
    }

    public void addContent(Content content) {
        this.content = content;
        content.addContentHeart(this);
    }
}