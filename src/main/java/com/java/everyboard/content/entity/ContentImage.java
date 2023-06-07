package com.java.everyboard.content.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor
public class ContentImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long contentImageId;

    @Column(nullable = false)
    private String contentImgUrl;

    public ContentImage(String contentImgUrl) {
        this.contentImgUrl = contentImgUrl;
    }
}
