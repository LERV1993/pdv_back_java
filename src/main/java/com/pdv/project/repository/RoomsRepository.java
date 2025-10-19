package com.pdv.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pdv.project.entity.RoomsEntity;

public interface RoomsRepository extends JpaRepository<RoomsEntity, Long> {
    
}
