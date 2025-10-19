package com.pdv.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pdv.project.entity.ArticlesEntity;
import java.util.List;


public interface ArticlesRepository extends JpaRepository<ArticlesEntity,Long> {

    List<ArticlesEntity> findByAvailableTrue();
    
}