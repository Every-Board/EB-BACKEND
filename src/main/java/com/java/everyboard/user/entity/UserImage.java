package com.java.everyboard.user.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class UserImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userImageId;

    private Long userId;
    private String userImgUrl;

    public UserImage(Long userId) {
        this.userId = userId;
    }

    public UserImage(Long userId, String userImgUrl) {
        this.userId = userId;
        this.userImgUrl = userImgUrl;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}