package com.java.everyboard.scrap.mapper;

import com.java.everyboard.content.entity.Content;
import com.java.everyboard.scrap.dto.*;
import com.java.everyboard.scrap.entity.Scrap;
import com.java.everyboard.user.entity.User;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ScrapMapper {

    // 스크랩 포스트 DTO TO 스크랩 //
    Scrap ScrapPatchDtoToScrap(ScrapPatchDto requestBody);

    // 스크랩 TO 스크랩 리스폰스 //
    default ScrapResponseDto scrapToScrapResponseDto(Scrap scrap) {
        User user = scrap.getUser();
        Content content = scrap.getContent();

        return ScrapResponseDto.builder()
                .userId(user.getUserId())
                .contentId(content.getContentId())
                .title(content.getTitle())
                .content(content.getContent())
                .scrapId(scrap.getScrapId())
                .scrapType(scrap.getScrapType().toString())
                .createdAt(content.getCreatedAt())
                .modifiedAt(content.getModifiedAt())
                .build();
    }
    // 스크랩 TO 스크랩 리스트 리스폰스 //
    List<ScrapListResponseDto> scrapsToScrapListResponseDtos(List<Scrap> scrap);
}
