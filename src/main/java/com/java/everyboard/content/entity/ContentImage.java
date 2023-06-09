package com.java.everyboard.content.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class ContentImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long contentImageId;

    private Long contentId;
    private String contentImgUrl;

    public ContentImage(String contentImgUrl) {
        this.contentImgUrl = contentImgUrl;
    }

    public ContentImage(Long contentId) {
        this.contentId = contentId;
    }

    public ContentImage(Long contentId, String contentImgUrl) {
        this.contentId = contentId;
        this.contentImgUrl = contentImgUrl;
    }

    public void setContentId(Long contentId) {
        this.contentId = contentId;
    }
}
