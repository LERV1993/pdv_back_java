package com.pdv.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.pdv.project.entity.ArticleEntity;

import jakarta.transaction.Transactional;

import java.util.List;

public interface ArticleRepository extends JpaRepository<ArticleEntity, Long> {

    List<ArticleEntity> findByAvailableTrue();

    @Modifying
    @Transactional
    @Query("""
                UPDATE ArticleEntity a
                SET a.available = false
                WHERE a.id IN :ids
            """)
    int setAvailableFalseForIds(@Param("ids") List<Long> ids);

}