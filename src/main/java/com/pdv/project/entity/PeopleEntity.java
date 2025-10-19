package com.pdv.project.entity;

import com.pdv.project.dto.request.PeopleRequestDTO;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "people")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PeopleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "person_name", nullable = false)
    private String name;

    @Column(name = "email", nullable = false)
    private String email;

    public static PeopleEntity fromRequest(PeopleRequestDTO request) {

        if (request == null) {
            return null;
        }

        return PeopleEntity.builder()
                .id(request.getId())
                .name(request.getName())
                .email(request.getEmail())
                .build();

    }

}