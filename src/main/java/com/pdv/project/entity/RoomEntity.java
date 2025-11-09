package com.pdv.project.entity;

import com.pdv.project.dto.request.RoomRequestDTO;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "rooms")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RoomEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "room_name", nullable = false)
    private String name;

    @Column(name = "room_capacity", nullable = false)
    private int capacity;

    public static RoomEntity fromRequest(RoomRequestDTO request){

        if(request == null){
            throw new IllegalArgumentException("The entity cannot be null.");
        }

        return RoomEntity.builder()
        .id(request.getId())
        .name(request.getName())
        .capacity(request.getCapacity())
        .build();

    }

}