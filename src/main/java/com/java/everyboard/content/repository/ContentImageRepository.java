package com.java.everyboard.content.repository;

import com.java.everyboard.content.entity.ContentImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ContentImageRepository extends JpaRepository<ContentImage, Long> {
    List<ContentImage> findByContentId(long contentId);
    @Query(value = "select * from content_image where user_id = :userId", nativeQuery = true)
    List<ContentImage> findByUserId(@Param("userId") long userId);
    @Modifying
    @Query(value = "delete from content_image where content_id=:contentId", nativeQuery = true)
    void deleteAllByContentId(@Param("contentId")Long contentId);
}