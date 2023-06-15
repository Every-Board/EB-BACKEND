package com.java.everyboard.user.mapper;

import com.java.everyboard.comment.entity.Comment;
import com.java.everyboard.comment.repository.CommentRepository;
import com.java.everyboard.content.dto.ContentResponseDto;
import com.java.everyboard.content.entity.Content;
import com.java.everyboard.content.entity.ContentImage;
import com.java.everyboard.content.repository.ContentImageRepository;
import com.java.everyboard.content.repository.ContentRepository;
import com.java.everyboard.contentHeart.dto.ContentHeartResponseDto;
import com.java.everyboard.contentHeart.entity.ContentHeart;
import com.java.everyboard.contentHeart.repository.ContentHeartRepository;
import com.java.everyboard.scrap.entity.Scrap;
import com.java.everyboard.scrap.repository.ScrapRepository;
import com.java.everyboard.user.dto.*;
import com.java.everyboard.user.entity.User;
import com.java.everyboard.user.entity.UserImage;
import com.java.everyboard.user.repository.UserImageRepository;
import org.mapstruct.Mapper;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User postDtoToUser(UserPostDto requestBody);

    User patchDtoToUser(UserPatchDto requestBody);

//    UserResponseDto userToUserResponseDto(User user);

    default UserResponseDto userToUserResponseDto(User user, UserImageRepository userImageRepository){
        List<UserImage> userImage = userImageRepository.findByUserId(user.getUserId());

        return UserResponseDto.builder()
                .userId(user.getUserId())
                .email(user.getEmail())
                .nickname(user.getNickname())
                .password(user.getPassword())
                .profileUrl(userImage)
                .loginType(user.getLoginType())
                .createdAt(user.getCreatedAt())
                .modifiedAt(user.getModifiedAt())
                .build();
    }

    default UserAllResponseDto userMyPage(User user, UserImageRepository userImageRepository, ContentRepository contentRepository, ContentImageRepository contentImageRepository, CommentRepository commentRepository, ContentHeartRepository contentHeartRepository, ScrapRepository scrapRepository){
        List<Content> contents = contentRepository.findByUserId(user.getUserId());
        Collections.reverse(contents);
        List<Comment> comments = commentRepository.findByUserId(user.getUserId());
        Collections.reverse(comments);
        List<ContentHeart> hearts = contentHeartRepository.findByUserId(user.getUserId());
        Collections.reverse(hearts);
        List<Scrap> scraps = scrapRepository.findByUserId(user.getUserId());
        Collections.reverse(hearts);

        return UserAllResponseDto.builder()
                .userId(user.getUserId())
                .email(user.getEmail())
                .password(user.getPassword())
                .nickname(user.getNickname())
                .profileUrl(user.getProfileUrl())
                .createdAt(user.getCreatedAt())
                .modifiedAt(user.getModifiedAt())
                .comments(commentsToCommentResponseDtos(comments))
                .contents(contentsToContentResponseDtos(contents, contentImageRepository, user))
                .hearts(heartsToHeartResponseDtos(hearts))
                .scraps(scrapsToScrapResponseDtos(scraps, contentImageRepository))
                .build();
    }
    default List<UserCommentResponseDto> commentsToCommentResponseDtos(List<Comment> comments){
        return comments.stream()
                .map(comment -> UserCommentResponseDto.builder()
                        .commentId(comment.getCommentId())
                        .contentId(comment.getContent().getContentId())
                        .title(comment.getContent().getTitle())
                        .createdAt(comment.getCreatedAt())
                        .modifiedAt(comment.getModifiedAt())
                        .comment(comment.getComment())
                        .build())
                .collect(Collectors.toList());
    }
    default List<UserContentResponseDto> contentsToContentResponseDtos(List<Content> contents, ContentImageRepository contentImageRepository, User user){
        return contents.stream()
                .map(content -> UserContentResponseDto.builder()
                        .contentId(content.getContentId())
                        .title(content.getTitle())
                        .content(content.getContent())
                        .contentImages(contentImageRepository.findByContentId(content.getContentId()))
                        .createdAt(content.getCreatedAt())
                        .modifiedAt(content.getModifiedAt())
                        .build())
                .collect(Collectors.toList());
    }
    default List<UserContentHeartResponseDto> heartsToHeartResponseDtos(List<ContentHeart> contentHearts){
        return contentHearts.stream()
                .map(contentHeart -> UserContentHeartResponseDto.builder()
                        .contentId(contentHeart.getContent().getContentId())
                        .title(contentHeart.getContent().getTitle())
                        .heartType(contentHeart.getHeartType())
                        .createdAt(contentHeart.getCreatedAt())
                        .modifiedAt(contentHeart.getModifiedAt())
                        .build())
                .collect(Collectors.toList());
    }
    default List<UserScrapResponseDto> scrapsToScrapResponseDtos(List<Scrap> scraps, ContentImageRepository contentImageRepository){
        return scraps.stream()
                .map(scrap -> UserScrapResponseDto.builder()
                        .scrapId(scrap.getScrapId())
                        .contentId(scrap.getContent().getContentId())
                        .title(scrap.getContent().getTitle())
                        .content(scrap.getContent().getContent())
                        .contentImages(scrap.getContent().getContentImages())
                        .contentImages(contentImageRepository.findByContentId(scrap.getContent().getContentId()))
                        .scrapType(scrap.getScrapType())
                        .createdAt(scrap.getCreatedAt())
                        .modifiedAt(scrap.getModifiedAt())
                        .build())
                .collect(Collectors.toList());
    }
}
