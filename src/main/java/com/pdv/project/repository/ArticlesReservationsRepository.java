package com.pdv.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pdv.project.entity.ArticlesReservationsEntity;

public interface ArticlesReservationsRepository extends JpaRepository<ArticlesReservationsEntity, Long>{

    
}