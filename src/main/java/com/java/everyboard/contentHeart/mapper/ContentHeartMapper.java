package com.java.everyboard.contentHeart.mapper;

import com.java.everyboard.content.entity.Content;
import com.java.everyboard.contentHeart.dto.ContentHeartPatchDto;
import com.java.everyboard.contentHeart.dto.ContentHeartResponseDto;
import com.java.everyboard.contentHeart.entity.ContentHeart;
import com.java.everyboard.user.entity.User;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ContentHeartMapper {
    ContentHeart ContentHeartPatchDtoToEntity(ContentHeartPatchDto requestBody);
    default ContentHeartResponseDto contentHeartToContentHeartResponseDto(ContentHeart contentHeart) {
        User user = contentHeart.getUser();
        Content content = contentHeart.getContent();

        return ContentHeartResponseDto.builder()
                .userId(user.getUserId())
                .contentHeartId(contentHeart.getContentHeartId())
                .contentId(content.getContentId())
                .heartType(contentHeart.getHeartType().toString())

                .build();
    }
    List<ContentHeartResponseDto> contentHeartsToContentHeartResponseDtos(List<ContentHeart> contentHeart);
}