package com.pdv.project.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "articles_reservations")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArticlesReservationsEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_articles", nullable = false)
    private ArticlesEntity articles;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_reservations", nullable = false)
    private ReservationEntity reservation;

}