package com.pdv.project.service;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.pdv.project.config.RabbitMQConfig;
import com.pdv.project.dto.request.ReservationRequestDTO;
import com.pdv.project.dto.response.ArticleResponseDTO;
import com.pdv.project.dto.response.PeopleResponseDTO;
import com.pdv.project.dto.response.ReservationDetailResponseDTO;
import com.pdv.project.dto.response.ReservationResponseDTO;
import com.pdv.project.dto.response.RoomResponseDTO;
import com.pdv.project.entity.ArticleEntity;
import com.pdv.project.entity.ArticleReservationEntity;
import com.pdv.project.entity.PeopleEntity;
import com.pdv.project.entity.ReservationEntity;
import com.pdv.project.entity.RoomEntity;
import com.pdv.project.repository.ArticleRepository;
import com.pdv.project.repository.ArticleReservationRepository;
import com.pdv.project.repository.PeopleRepository;
import com.pdv.project.repository.ReservationRepository;
import com.pdv.project.repository.RoomsRepository;
import com.pdv.project.utils.DataTimeUtils;

import org.springframework.amqp.rabbit.core.RabbitTemplate;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReservationsService {

     private final RabbitTemplate rabbitTemplate;

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

        if (!this.articlesExistAndStillAvailable(request)) {
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

        ReservationResponseDTO response = ReservationResponseDTO.fromEntity(reservationEntitySaved);

        rabbitTemplate.convertAndSend(RabbitMQConfig.QUEUE_NAME, response );

        return response;
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

    public ReservationDetailResponseDTO getReservationDetails(Long id) {

        ReservationEntity reservation = reservationRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Reservation not found with id " + id));

        return this.reservationToReservationDetails(reservation);
    }

    public  List<ReservationDetailResponseDTO> getReservationDetailsByPersonId(Long id) {

        List<ReservationEntity> reservation = reservationRepository.findByPeople_Id(id);
        
        if(reservation.isEmpty()){
            new EntityNotFoundException("Reservation not found with people id " + id);
        }

        return reservation.stream().map(r-> this.reservationToReservationDetails(r)).toList();
    }

    public List<ReservationDetailResponseDTO> getAllReservationDetails() {

        List<ReservationEntity> reservation = reservationRepository.findAll();
        return reservation.stream().map(r-> this.reservationToReservationDetails(r)).toList();
    }

    private boolean peopleExist(Long id_people) {
        return peopleRepository.findById(id_people).isPresent();
    }

    private boolean roomExist(Long id_room) {
        return roomsRepository.findById(id_room).isPresent();
    }

    private boolean articlesExistAndStillAvailable(ReservationRequestDTO request) {

        List<Long> id_articles = request.getIds_articles();

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

        if (this.articlesReservationRepository.hasOverlappingArticlesReservation(id_articles, request.getId(), request.getDate_hour_start(), request.getDate_hour_end())) {
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

    private ReservationDetailResponseDTO reservationToReservationDetails(ReservationEntity reservation){

        List<ArticleReservationEntity> articlesReservationEntity = articlesReservationRepository.findByReservation(reservation);
        List<ArticleResponseDTO> articleResponse = articlesReservationEntity.stream()
                .map(ar -> ArticleResponseDTO.fromEntity(ar.getArticle()))
                .toList();

        RoomEntity room = roomsRepository.findById(reservation.getRoom().getId())
                .orElseThrow(() -> new EntityNotFoundException("Room not found with id " + reservation.getRoom().getId()));

        PeopleEntity people = peopleRepository.findById(reservation.getPeople().getId())
                .orElseThrow(() -> new EntityNotFoundException("People not found with id " + reservation.getPeople().getId()));

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        return ReservationDetailResponseDTO.builder()
                .id(reservation.getId())
                .people(PeopleResponseDTO.fromEntity(people))
                .room(RoomResponseDTO.fromEntity(room))
                .date_hour_start(reservation.getDate_time_start().format(formatter))
                .date_hour_end(reservation.getDate_time_end().format(formatter))
                .articles(articleResponse)
                .build();
    }


}
