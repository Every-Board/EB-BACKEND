package com.java.everyboard.content.entity;

import com.java.everyboard.audit.Auditable;
import com.java.everyboard.comment.entity.Comment;
import com.java.everyboard.constant.Category;
import com.java.everyboard.contentHeart.entity.ContentHeart;
import com.java.everyboard.reply.entity.Reply;
import com.java.everyboard.scrap.entity.Scrap;
import com.java.everyboard.user.entity.User;
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
@Table(name = "CONTENTS")
public class Content extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CONTENT_ID")
    private Long contentId;

    @Column(nullable = false)
    private long viewCount = 0; // 조회수
    @Column(nullable = false)
    private long contentHeartCount = 0; // 좋아요수
    @Column(nullable = false)
    private String title; // 컨텐츠 제목
    @Column(nullable = false)
    private String content; // 컨텐츠 내용
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Category category; // 카테고리 (Enum값으로 받음)
//    @Column(nullable = false)
//    private String tag;
    @Transient
    private final List<ContentImage> contentImages = new ArrayList<>(); // 컨텐츠 이미지 리스트값

    // 연관 관계 M:N //
    @ManyToOne(optional = true, fetch = FetchType.EAGER, cascade = CascadeType.DETACH)
    @JoinColumn(name = "USER_ID")
    private User user;

    // 연관 관계 N:M //
    @OrderBy("contentHeartId")
    @OneToMany(mappedBy = "content", cascade = CascadeType.REMOVE)
    private List<ContentHeart> contentHearts = new ArrayList<>();


    @OneToMany(mappedBy = "content", cascade = CascadeType.REMOVE)
    private List<Comment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "content", cascade = CascadeType.REMOVE)
    private List<Reply> replies = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
    private List<Scrap> scraps = new ArrayList<>();

    // 연관 관계 메서드 //
    public void addContentHeart(ContentHeart contentHeart) {
        contentHearts.add(contentHeart);
    }
    public void addScrap(Scrap scrap) {
        scraps.add(scrap);
    }
}
