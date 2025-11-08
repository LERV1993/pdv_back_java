package com.pdv.project.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.pdv.project.entity.ReservationEntity;

@Repository
public interface ReservationRepository extends JpaRepository<ReservationEntity, Long> {

    List<ReservationEntity> findByPeople_Id(Long peopleId);

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

    @Query("""
                SELECT re
                FROM ReservationEntity re
                WHERE re.date_time_start <= :startDate
                  AND re.date_time_end > :startDate
            """)
    List<ReservationEntity> getReservationsIncluded(@Param("startDate") LocalDateTime startDate);

}