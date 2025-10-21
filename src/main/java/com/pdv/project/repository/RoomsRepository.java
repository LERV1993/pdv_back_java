package com.pdv.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pdv.project.entity.RoomEntity;

public interface RoomsRepository extends JpaRepository<RoomEntity, Long> {
    
}
