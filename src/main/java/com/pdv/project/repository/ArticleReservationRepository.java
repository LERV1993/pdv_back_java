package com.pdv.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pdv.project.entity.ArticleReservationEntity;

public interface ArticleReservationRepository extends JpaRepository<ArticleReservationEntity, Long>{

    void deleteByReservationId(Long reservationId);
    
}