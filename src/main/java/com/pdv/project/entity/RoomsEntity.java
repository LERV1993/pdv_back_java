package com.pdv.project.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "rooms")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoomsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "room_name", nullable = false)
    private String name;

    @Column(name = "room_capacity", nullable = false)
    private int capacity;

}