package com.java.everyboard.content.entity;

import com.java.everyboard.audit.Auditable;
import com.java.everyboard.constant.Category;
import com.java.everyboard.user.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;

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
    private long heartCount = 0; // 좋아요수
    @Column(nullable = false)
    private String title; // 컨텐츠 제목
    @Column(nullable = false)
    private String content; // 컨텐츠 내용
    @Column(nullable = false)
    private String imageUrl; // 컨텐츠 이미지 주소(S3에 저장된 이미지 주소값)
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Category category = Category.Board; // 카테고리 (Enum값으로 받음)
    @Column(nullable = false)
    private String tag;

    // 연관 관계 M:N //
    @ManyToOne(optional = true, fetch = FetchType.EAGER, cascade = CascadeType.DETACH)
    @JoinColumn(name = "USER_ID")
    private User user;

    // 연관 관계 N:M //
   /* @OrderBy("heartId")
    @OneToMany(mappedBy = "content", cascade = CascadeType.REMOVE)
    private List<Heart> hearts = new ArrayList<>();

    @OneToMany(mappedBy = "content", cascade = CascadeType.REMOVE)
    private List<Comment> comments = new ArrayList<>();*/

    // 생성자 //

    // 연관 관계 메서드 //

}
