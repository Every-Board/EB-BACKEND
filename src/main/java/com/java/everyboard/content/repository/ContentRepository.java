package com.java.everyboard.content.repository;

import com.java.everyboard.content.contentEnum.Category;
import com.java.everyboard.content.entity.Content;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ContentRepository extends JpaRepository<Content, Long> {
    @Query(value = "select * from content where category = :category", nativeQuery = true)
    List<Content> findAllByCategory(Category category);

    @Query(value = "select * from contents where user_id = :userId", nativeQuery = true)
    List<Content> findAllByUserId(long userId);
}
