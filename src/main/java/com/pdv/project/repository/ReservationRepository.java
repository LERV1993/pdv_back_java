package com.pdv.project.repository;

import java.time.LocalDateTime;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.pdv.project.entity.ReservationEntity;

public interface ReservationRepository extends JpaRepository<ReservationEntity, Long> {

        @Query("""
                            SELECT COUNT(r) > 0
                            FROM ReservationEntity r
                            WHERE r.room.id = :roomId
                              AND r.date_time_start < :reservationEnd
                              AND r.date_time_end > :reservationStart
                        """)
        boolean overlappingReserveExistsForCreate(
                        @Param("roomId") Long roomId,
                        @Param("reservationStart") LocalDateTime reservationStart,
                        @Param("reservationEnd") LocalDateTime reservationEnd);

        @Query("""
                            SELECT COUNT(r) > 0
                            FROM ReservationEntity r
                            WHERE r.id != :id
                              AND r.room.id = :roomId
                              AND r.date_time_start < :reservationEnd
                              AND r.date_time_end > :reservationStart
                        """)
        boolean overlappingReserveExists(
                        @Param("id") Long id,
                        @Param("roomId") Long roomId,
                        @Param("reservationStart") LocalDateTime reservationStart,
                        @Param("reservationEnd") LocalDateTime reservationEnd);
}