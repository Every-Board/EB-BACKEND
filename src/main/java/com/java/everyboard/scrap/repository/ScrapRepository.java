package com.java.everyboard.scrap.repository;

import com.java.everyboard.content.entity.Content;
import com.java.everyboard.scrap.entity.Scrap;
import com.java.everyboard.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ScrapRepository extends JpaRepository<Scrap, Long> {
    Optional<Scrap> findByUserAndContent(User user, Content content);

    @Query(value = "select * from scrap where user_id = :userId", nativeQuery = true)
    List<Scrap> findByUserId(@Param("userId") long userId);
}
