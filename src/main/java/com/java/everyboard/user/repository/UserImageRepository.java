package com.java.everyboard.user.repository;

import com.java.everyboard.content.entity.ContentImage;
import com.java.everyboard.user.entity.UserImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserImageRepository extends JpaRepository<UserImage, Long> {
    @Query(value = "select * from user_image where user_id = :userId", nativeQuery = true)
//    UserImage findByUserId(@Param("userId") long userId);
    List<UserImage> findByUserId(@Param("userId") long userId);
    @Modifying
    @Query(value = "delete from user_image where user_id=:userId", nativeQuery = true)
    void deleteAllByUserId(@Param("userId")Long userId);
}