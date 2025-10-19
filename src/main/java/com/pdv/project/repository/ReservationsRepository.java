package com.pdv.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pdv.project.entity.ReservationEntity;

public interface ReservationsRepository extends JpaRepository<ReservationEntity, Long> {
    
}