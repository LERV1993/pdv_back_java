package com.pdv.project.entity;

import java.time.LocalDateTime;

import com.pdv.project.dto.request.ReservationRequestDTO;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "reservations")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReservationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_rooms", nullable = false)
    private RoomEntity room;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_people", nullable = false)
    private PeopleEntity people;

    @Column(name = "reservation_start", nullable = false)
    private LocalDateTime date_time_start;

    @Column(name = "reservation_end", nullable = false)
    private LocalDateTime date_time_end;

    public static ReservationEntity fromRequest(ReservationRequestDTO request){

        if(request == null){
            return null;
        }

        RoomEntity room = RoomEntity.builder()
            .id(request.getId_room())
            .build();

        PeopleEntity people = PeopleEntity.builder()
            .id(request.getId_people())
            .build();

        return ReservationEntity.builder()
            .id(request.getId())
            .room(room)
            .people(people)
            .date_time_start(request.getDate_hour_start())
            .date_time_end(request.getDate_hour_end())
            .build();

    }

}