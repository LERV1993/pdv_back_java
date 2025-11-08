package com.pdv.project.service;

import java.util.List;
import java.util.Objects;

import org.springframework.stereotype.Service;

import com.pdv.project.dto.request.ArticlesAvailablesRequestDTO;
import com.pdv.project.dto.response.ArticleResponseDTO;
import com.pdv.project.entity.ArticleEntity;
import com.pdv.project.entity.ArticleReservationEntity;
import com.pdv.project.entity.ReservationEntity;
import com.pdv.project.repository.ArticleRepository;
import com.pdv.project.repository.ArticleReservationRepository;
import com.pdv.project.repository.ReservationRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class ArticleReservationService {

    private final ArticleRepository articleRepository;
    private final ReservationRepository reservationRepository;
    private final ArticleReservationRepository repository;

    public List<ArticleResponseDTO> getArticlesAvailablesForReservation(ArticlesAvailablesRequestDTO request) {

        List<ReservationEntity> reservations = this.reservationRepository.getReservationsIncluded(request.getDate());

        if (reservations.isEmpty()) {

            log.info("Article Reservation Service - Get Articles Availables for Reservation: The selected date did not match any results.");

            List<ArticleEntity> articleEntityList = this.articleRepository.findAll();
            return articleEntityList.stream()
                    .map(ArticleResponseDTO::fromEntity)
                    .filter(Objects::nonNull)
                    .toList();

        }

        log.info("hay: {}",reservations.size());

        List<ArticleReservationEntity> reservationArticles = this.repository.findByReservationIn(reservations);

        if (reservationArticles.isEmpty()) {

            log.info("Article Reservation Service - Get Articles Availables for Reservation: Matching reservations do not have articles.");

            List<ArticleEntity> articleEntityList = this.articleRepository.findAll();
            return articleEntityList.stream()
                    .map(ArticleResponseDTO::fromEntity)
                    .filter(Objects::nonNull)
                    .toList();

        }

        List<Long> ids = reservationArticles.stream().map(articleReserved -> articleReserved.getArticle().getId()).toList();

        List<ArticleEntity> articlesAvailables = this.articleRepository.findByIdNotIn(ids);

        return articlesAvailables.stream()
                .map(ArticleResponseDTO::fromEntity)
                .filter(Objects::nonNull)
                .toList();

    }
}
