package com.java.everyboard.contentHeart.repository;

import com.java.everyboard.content.entity.Content;
import com.java.everyboard.contentHeart.entity.ContentHeart;
import com.java.everyboard.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ContentHeartRepository extends JpaRepository<ContentHeart, Long> {
    //    List<Heart> findAllByUserAndContent(User user, Content content);
//    List<Heart> findAllByContent(Content content);
    Optional<ContentHeart> findByUserAndContent(User user, Content content);

    @Query(value = "select * from content_heart where user_id = :userId", nativeQuery = true)
    List<ContentHeart> findByUserId(@Param("userId") long userId);
}