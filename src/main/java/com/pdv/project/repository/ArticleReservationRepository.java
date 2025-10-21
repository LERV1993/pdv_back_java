package com.pdv.project.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.pdv.project.entity.ArticleReservationEntity;

public interface ArticleReservationRepository extends JpaRepository<ArticleReservationEntity, Long> {

    void deleteByReservationId(Long reservationId);

    @Query("""
                SELECT COUNT(ar) > 0
                FROM ArticleReservationEntity ar
                WHERE ar.article.id IN :articleIds
                  AND (:excludeReservationId IS NULL OR ar.reservation.id <> :excludeReservationId)
                  AND ar.reservation.date_time_start < :reservationEnd
                  AND ar.reservation.date_time_end > :reservationStart
            """)
    boolean hasOverlappingArticlesReservation(
            @Param("articleIds") List<Long> articleIds,
            @Param("excludeReservationId") Long excludeReservationId,
            @Param("reservationStart") LocalDateTime reservationStart,
            @Param("reservationEnd") LocalDateTime reservationEnd);

}