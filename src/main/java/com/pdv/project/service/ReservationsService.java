package com.pdv.project.service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.stereotype.Service;
import com.pdv.project.dto.request.ReservationRequestDTO;
import com.pdv.project.dto.response.ReservationResponseDTO;
import com.pdv.project.entity.ArticleEntity;
import com.pdv.project.entity.ArticleReservationEntity;
import com.pdv.project.entity.ReservationEntity;
import com.pdv.project.repository.ArticleRepository;
import com.pdv.project.repository.ArticleReservationRepository;
import com.pdv.project.repository.PeopleRepository;
import com.pdv.project.repository.ReservationRepository;
import com.pdv.project.repository.RoomsRepository;
import com.pdv.project.utils.DataTimeUtils;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReservationsService {

    private final ReservationRepository reservationRepository;
    private final PeopleRepository peopleRepository;
    private final RoomsRepository roomsRepository;
    private final ArticleRepository articlesRepository;
    private final ArticleReservationRepository articlesReservationRepository;

    public List<ReservationResponseDTO> getReservations() {

        List<ReservationEntity> reservationsEntityList = this.reservationRepository.findAll();

        return reservationsEntityList.stream()
                .map(ReservationResponseDTO::fromEntity)
                .filter(Objects::nonNull)
                .toList();

    }

    @Transactional
    public ReservationResponseDTO addEditReservations(ReservationRequestDTO request) {

        if (request == null) {
            log.info("Reservations Service - Add reservations: request is null.");
            return null;
        }

        if(request.getId() != null){
            articlesReservationRepository.deleteByReservationId(request.getId());
        }

        if (!DataTimeUtils.validateDate(request.getDate_hour_start())
                || !DataTimeUtils.validateDate(request.getDate_hour_end())
                || !request.getDate_hour_end().isAfter(request.getDate_hour_start())) {

            log.info("Reservations Service - Add reservations: The dates selected for the reservation are not valid.");
            return null;
        }

        if (!this.peopleExist(request.getId_people())) {
            log.info("Reservation Service - Add/Edit: The selected person ID does not exist in the system.");
            return null;
        }

        if (!this.roomExist(request.getId_room())) {
            log.info("Reservation Service - Add/Edit: The selected room ID does not exist in the system.");
            return null;
        }

        if (!this.articlesExistAndStillAvailable(request.getIds_articles())) {
            log.info("Reservation Service - Add/Edit: Some articles do not exist or are unavailable.");
            return null;
        }

        if (this.validateReservation(request)) {
            log.info("Reservation Service - Add/Edit: The selected reservation overlaps with an existing one.");
            return null;
        }

        ReservationEntity reservationEntity = ReservationEntity.fromRequest(request);
        ReservationEntity reservationEntitySaved = this.reservationRepository.save(reservationEntity);
        List<ArticleReservationEntity> articleReservations = ArticleReservationEntity
                .fromReservationEntityAndRequest(reservationEntitySaved, request);
        this.articlesReservationRepository.saveAll(articleReservations);
        this.articlesRepository.setAvailableFalseForIds(request.getIds_articles());

        return ReservationResponseDTO.fromEntity(reservationEntitySaved);
    }

    @Transactional
    public ReservationResponseDTO deleteReservations(Long id) {

        if(id == null){
            log.info("Reservation Service - Delete Reservation: id null.");
            return null;
        }

        Optional<ReservationEntity> entity = this.reservationRepository.findById(id);

        if (entity.isPresent()) {
            articlesReservationRepository.deleteByReservationId(id);
            reservationRepository.deleteById(id);
            return ReservationResponseDTO.fromEntity(entity.get());
        }

        log.info("Reservation Service - Delete Reservation: The record for id {}, no exist.", id);
        return null; 
    }

    private boolean peopleExist(Long id_people) {
        return peopleRepository.findById(id_people).isPresent();
    }

    private boolean roomExist(Long id_room) {
        return roomsRepository.findById(id_room).isPresent();
    }

    private boolean articlesExistAndStillAvailable(List<Long> id_articles) {

        if (id_articles.isEmpty()) {
            log.info("Reservation Service - Articles Exist And Still Available: No IDs have been provided.");
            return true;
        }

        if (id_articles.contains(null)) {
            log.info("Reservation Service - Articles Exist And Still Available: Some ID provided is null.");
            return false;
        }

        List<ArticleEntity> articles = this.articlesRepository.findAllById(id_articles);

        if (articles.size() != id_articles.size()) {
            log.info("Reservation Service - Articles Exist And Still Available: Expected {} articles, but {} were returned.",
                    id_articles.size(), articles.size());
            return false;
        }

        boolean allAvailable = articles.stream().allMatch(ArticleEntity::getAvailable);
        if (!allAvailable) {
            log.info("Reservation Service - Articles Exist And Still Available: Some of the selected items are not available for reservation.");
            return false;
        }

        return true;

    }

    private boolean validateReservation(ReservationRequestDTO request) {

        if (request.getId() != null) {
            return this.reservationRepository.overlappingReserveExists(
                request.getId(),    
                request.getId_room(),
                request.getDate_hour_start(),
                request.getDate_hour_end());
        }

        return this.reservationRepository.overlappingReserveExistsForCreate(request.getId_room(),
                request.getDate_hour_start(),
                request.getDate_hour_end());

    }

}
