package com.pdv.project.repository;

import org.springframework.stereotype.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pdv.project.entity.AuthCredentialsEntity;

@Repository
public interface AuthCredentialsRepository extends JpaRepository<AuthCredentialsEntity, Long> {

    Optional<AuthCredentialsEntity> findByEmail(String email);

    boolean existsByEmail(String email);

}