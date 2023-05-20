package com.java.everyboard.user.entity;

import com.java.everyboard.audit.Auditable;
import com.java.everyboard.comment.entity.Comment;
import com.java.everyboard.commentheart.entity.CommentHeart;
import com.java.everyboard.constant.ActiveStatus;
import com.java.everyboard.constant.LoginType;
import com.java.everyboard.content.entity.Content;
import com.java.everyboard.contentHeart.entity.ContentHeart;
import com.java.everyboard.scrap.entity.Scrap;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "USERS")
public class User extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String nickname;

    @Column(nullable = false)
    private String password;

    @Column
    private String profileUrl;

    @Column
    private String profileKey;

    @Column
    @Enumerated(EnumType.STRING)
    private LoginType loginType = LoginType.BASIC;

    @Column
    @Enumerated(EnumType.STRING)
    private ActiveStatus activeStatus = ActiveStatus.ACTIVE;

    // 연관 관계 //
    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
    private List<Content> contents = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
    private List<Comment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
    private List<ContentHeart> contentHearts = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
    private List<CommentHeart> commentHearts = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
    private List<Scrap> scraps = new ArrayList<>();

    // 연관 관계 메서드 //
    public void addContentHeart(ContentHeart contentHeart) {
        contentHearts.add(contentHeart);
    }
    public void addCommentHeart(CommentHeart commentHeart) { commentHearts.add(commentHeart); }
    public void addScrap(Scrap scrap) {
        scraps.add(scrap);
    }
}