package com.pdv.project.entity;

import java.util.ArrayList;
import java.util.List;

import com.pdv.project.dto.request.ReservationRequestDTO;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "articles_reservations")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ArticleReservationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_article", nullable = false)
    private ArticleEntity article;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_reservations", nullable = false)
    private ReservationEntity reservation;

    public static List<ArticleReservationEntity> fromReservationEntityAndRequest(ReservationEntity reservationentity, ReservationRequestDTO request) {

        if (reservationentity == null) {
            return null;
        }

        List<ArticleReservationEntity> listArticlesReservation = new ArrayList<>();

        for (Long article_id : request.getIds_articles()) {

            ArticleEntity article = ArticleEntity.builder().id(article_id).build();

            listArticlesReservation.add(
                    ArticleReservationEntity.builder()
                            .article(article)
                            .reservation(reservationentity)
                            .build());

        }

        return listArticlesReservation;

    }

}