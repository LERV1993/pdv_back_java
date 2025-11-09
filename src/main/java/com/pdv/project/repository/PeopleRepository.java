package com.pdv.project.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pdv.project.entity.PeopleEntity;

@Repository
public interface PeopleRepository extends JpaRepository<PeopleEntity,Long> {

    Optional<PeopleEntity> findByEmail(String email);
    
}