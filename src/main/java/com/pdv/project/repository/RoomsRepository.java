package com.pdv.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pdv.project.entity.RoomEntity;

@Repository
public interface RoomsRepository extends JpaRepository<RoomEntity, Long> {
    
}
