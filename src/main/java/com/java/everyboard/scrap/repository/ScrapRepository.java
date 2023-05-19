package com.java.everyboard.scrap.repository;

import com.java.everyboard.content.entity.Content;
import com.java.everyboard.contentHeart.entity.ContentHeart;
import com.java.everyboard.scrap.entity.Scrap;
import com.java.everyboard.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ScrapRepository extends JpaRepository<Scrap, Long> {
    Optional<Scrap> findByUserAndContent(User user, Content content);

    @Query(value = "select * from scrap where user_id = :userId", nativeQuery = true)
    List<Scrap> findAllByUserId(long userId);
}
