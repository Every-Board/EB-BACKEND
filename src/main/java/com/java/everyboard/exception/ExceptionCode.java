package com.java.everyboard.exception;

import lombok.Getter;

public enum ExceptionCode {

    // common
    UNAUTHORIZED(401, "Unauthorized"),
    ACCESS_FORBIDDEN(403, "Access forbidden"),
    METHOD_NOT_ALLOWED(405, "Method Not Allowed"),
    INTERNAL_SERVER_ERROR(500, "Internal Server Error"),
    NOT_IMPLEMENTATION(501, "Not Implementation"),
    INVALID_VALUES(400, "Invalid Values"),
    INVALID_DATE(400, "Invalid Date"),
    EMAIL_EXISTS(409, "Email exists"),

    // auth
    INVALID_EMAIL_AUTH_NUMBER(400, "Invalid email authNumber"),
    INVALID_EMAIL_AUTH(400, "Invalid email auth"),
    INVALID_REFRESH_TOKEN(400, "Invalid refresh token"),
    EXPIRED_JWT_TOKEN(421, "Expired jwt token"),
    EMAIL_AUTH_REQUIRED(403, "Email auth required"),

    // user
    INVALID_MEMBER_STATUS(400, "Invalid member status"),
    PROFILE_IMAGE_NOT_FOUND(404,  "Profile 이미지가 업로드 되지 않았습니다."),
    MAX_FILE_SIZE_2MB(400, "Max file size 2MB"),
    MEMBER_EXISTS(409, "Member exists"),
    MEMBER_NOT_FOUND(404, "Member not found"),
    INVALID_PASSWORD (400, "Invalid Password"),
    MEMBER_NOT_LOGIN(400, "Member not login"),
    ID_NOT_EXIST(404, "Id not exist"),

    // content (게시글)
    CONTENT_IMAGE_NOT_FOUND(404,  "CONTENT 이미지가 업로드 되지 않았습니다."),
    CONTENT_IMAGE_CHECK_ERROR(404,  "CONTENT 이미지 경로가 확인되지 않았습니다."),
    CONTENT_NOT_PATCHED(403, "Content not patched"),
    CONTENT_NOT_FOUND(404, "Content Not Found"),
    CONTENT_CHECK_EXISTS(409, "Content Check exists"),
    CONTENT_EXISTS(409, "CONTENT exists"),
    CONTENT_REQUEST_EXISTS(409, "CONTENT Request exists"),
    CONTENT_NOT_RECRUITING(403, "CONTENT status not recruiting"),
    INVALID_CONTENT_STATUS(403, "Invalid CONTENT Status"),
    CONTENT_MEMBER_EXISTS(409, "CONTENT Member exists"),

    // CONTENT HEART (게시글 좋아요)
    CONTENT_HEART_NOT_FOUND(404, "CONTENT HEART not found"),
    // SCRAP (스크랩)
    SCRAP_NOT_FOUND(404, "SCRAP not found"),
    // offer
    OFFER_NOT_PATCHED(403, "Offer not patched"),
    OFFER_NOT_FOUND(404, "Offer Not Found"),
    OFFER_CHECK_EXISTS(409, "Offer Check exists"),
    OFFER_EXISTS(409, "Offer exists"),

    // comment
    COMMENT_NOT_PATCHED(403, "Comment not patched"),
    COMMENT_NOT_FOUND(404, "Comment Not Found"),
    COMMENT_CHECK_EXISTS(409, "Comment Check exists"),

    // Heart
    HEART_NOT_FOUND(404, "Heart Not Found"),

    // stack
    STACK_NOT_PATCHED(403, "Stack not patched"),
    STACK_NOT_FOUND(404, "Stack Not Found"),
    STACK_CHECK_EXISTS(409, "Stack Check exists"),
    STACK_EXISTS(409, "Stack exists"),

    // CONTENT_Task
    TASK_NOT_PATCHED(403, "Task not patched"),
    TASK_NOT_FOUND(404, "Task Not Found"),
    TASK_CHECK_EXISTS(409, "Task Check exists"),
    TASK_EXISTS(409, "Task exists");


    @Getter
    private int status;

    @Getter
    private String message;

    ExceptionCode(int status, String message) {
        this.status = status;
        this.message = message;
    }
}
