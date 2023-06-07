package com.java.everyboard.content.repository;

import com.java.everyboard.constant.Category;
import com.java.everyboard.content.entity.Content;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ContentRepository extends JpaRepository<Content, Long> {
    List<Content> findAllByCategory(Category category);
    Optional<Content> findByContentId(long contentId);

    @Query(value = "select * from contents where user_id = :userId", nativeQuery = true)
    List<Content> findAllByUserId(long userId);

    // 게시글 조회수 상위 조회//

    // today(현재시간-24 ~ 현재시간)
    @Query(value = "select * from contents order by view_count desc limit 10", nativeQuery = true)
    List<Content> findContentsTodayViewRank();

    // weekly(현재시간 -일주일 ~ 현재시간)
    @Query(value = "select * from contents order by view_count desc limit 10", nativeQuery = true)
    List<Content> findContentsWeeklyViewRank();

    // 좋아요 상위
    @Query(value = "select * from contents order by content_heart_count desc limit 10", nativeQuery = true)
    List<Content> findContentsLikeRank();

    // 홈페이지 최신 이미지
    @Query(value = "select * from contents order by create_at asc", nativeQuery = true)
    List<Content> findContentsRecentImage();

    // 게시글 검색기능
    @Query(value = "select * from contents WHERE title LIKE %:keyword% OR content LIKE %:keyword% ", nativeQuery = true)
    List<Content> findAllSearch(@Param(value = "keyword")String keyword);
}