package com.java.everyboard.content.entity;

import com.java.everyboard.content.contentEnum.Category;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "CONTENTS")
public class Content {
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
    private Category category; // 카테고리 (Enum값으로 받음)
    @Column(nullable = false)
    private String tag; // 카테고리 (Enum값으로 받음)

    @CreatedDate //데이터 생성 날짜 자동 저장 어노테이션
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt; // 컨텐츠 생성 시간
    @LastModifiedDate // 데이터 수정 날짜 자동 저장 어노테이션
    @Column(name = "LAST_MODIFIED_AT")
    private LocalDateTime modifiedAt; // 컨텐츠 수정 시간

    // 연관 관계 //

    // 생성자 //

    // 연관 관계 메서드 //

}
