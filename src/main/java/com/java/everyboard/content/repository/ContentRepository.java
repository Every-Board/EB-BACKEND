package com.java.everyboard.content.repository;

import com.java.everyboard.constant.Category;
import com.java.everyboard.content.entity.Content;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ContentRepository extends JpaRepository<Content, Long> {
    List<Content> findAll();
    List<Content> findAllByCategory(Category category);
    @Query(value = "select * from contents where user_id = :userId", nativeQuery = true)
    List<Content> findByUserId(@Param("userId") long userId);
    @Query(value = "select * from contents as u inner join scrap as b on u.user_id = b.user_id where u.user_id = :userId", nativeQuery = true)
    List<Content> findAllByScraps(@Param("userId") long userId);
    Optional<Content> findByContentId(long contentId);

    // 게시글 조회수 상위 조회//

    // today(현재시간-24 ~ 현재시간)
    @Query(value = "select * from contents where created_at BETWEEN TIMESTAMPADD(hour, -24, NOW()) AND NOW() order by view_count desc limit 10", nativeQuery = true)
    List<Content> findContentsTodayViewRank();

    // weekly(현재시간 -일주일 ~ 현재시간)
    @Query(value = "select * from contents where created_at BETWEEN TIMESTAMPADD(day, -7, NOW()) AND NOW() order by view_count desc limit 10", nativeQuery = true)
    List<Content> findContentsWeeklyViewRank();

    // 좋아요 상위
    // 이번주 좋아요 상위
    @Query(value = "select * from contents where created_at BETWEEN TIMESTAMPADD(day, -7, NOW()) AND NOW() order by content_heart_count desc limit 10", nativeQuery = true)
    List<Content> findContentsLikeRank();

    // 홈페이지 최신 이미지
    @Query(value = "select * from contents order by created_at desc", nativeQuery = true)
    List<Content> findContentsRecentImage();

    // 게시글 검색기능
    @Query(value = "select * from contents where title like %:keyword% or content like %:keyword% ", nativeQuery = true)
    List<Content> findAllSearch(@Param(value = "keyword")String keyword);
}