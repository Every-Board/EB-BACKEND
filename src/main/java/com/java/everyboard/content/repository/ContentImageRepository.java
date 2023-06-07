package com.java.everyboard.content.repository;

import com.java.everyboard.constant.Category;
import com.java.everyboard.content.entity.Content;
import com.java.everyboard.content.entity.ContentImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ContentImageRepository extends JpaRepository<ContentImage, Long> {
    List<ContentImage> findByContentImageId(long contentImageId);
}