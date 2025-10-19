package com.pdv.project.entity;

import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "reservations")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReservationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_rooms", nullable = false)
    private RoomsEntity room;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_people", nullable = false)
    private PeopleEntity people;

    @Column(name = "reservation_start", nullable = false)
    private LocalDateTime date_time_start;

    @Column(name = "reservation_end", nullable = false)
    private LocalDateTime date_time_end;

}