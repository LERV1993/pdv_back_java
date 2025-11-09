package com.pdv.project.dto.response;

import java.io.Serializable;

import com.pdv.project.entity.ReservationEntity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "Reservation record corresponding to a room.")
public class ReservationResponseDTO implements Serializable {

    @Schema(description = "Unique identifier assigned to the record", example = "1")
    private Long id;

    @Schema(description = "Unique identifier assigned to the room", example = "3")
    private Long id_room;

    @Schema(description = "Unique identifier assigned to the people", example = "5")
    private Long id_people;

    @Schema(description = "Date and time of reservation start.", example = "2025-10-18 11:00:00")
    private String date_hour_start;

    @Schema(description = "Date and time of reservation end.", example = "2025-10-18 12:00:00")
    private String date_hour_end;

    public static ReservationResponseDTO fromEntity(ReservationEntity entity) {

        if (entity == null) {
            throw new IllegalArgumentException("The entity cannot be null.");
        }

        return ReservationResponseDTO.builder()
                .id(entity.getId())
                .id_room(entity.getRoom().getId())
                .id_people(entity.getPeople().getId())
                .date_hour_start(entity.getDate_time_start().toString())
                .date_hour_end(entity.getDate_time_end().toString())
                .build();

    }

}