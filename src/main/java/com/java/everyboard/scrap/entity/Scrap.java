package com.java.everyboard.scrap.entity;

import com.java.everyboard.audit.Auditable;
import com.java.everyboard.content.entity.Content;
import com.java.everyboard.scrap.constant.ScrapType;
import com.java.everyboard.user.User;
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
public class Scrap extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ScrapId;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private ScrapType scrapType;

    public Scrap(User user, Content content) {
        this.user = user;
        this.content = content;
    }

    // 연관 관계 //
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "content_id")
    private Content content;

    // 연관 관계 메소드 //
    public void addUser(User user) {
        this.user = user;
        user.addScrap(this);
    }

    public void addContent(Content content) {
        this.content = content;
        content.addScrap(this);
    }
}