package com.pdv.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pdv.project.entity.PeopleEntity;

public interface PeopleRepository extends JpaRepository<PeopleEntity,Long> {
    
}