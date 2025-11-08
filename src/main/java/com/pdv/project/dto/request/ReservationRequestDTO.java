package com.pdv.project.dto.request;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "New reservation request for regsitration or reservation record to edit with id.")
public class ReservationRequestDTO {

    @Min(value = 0, message = "The id cannot be negative")
    @Max(value = 9223372036854775807L, message = "The id exceeds the maximum allowed")
    @Schema(description = "The id for the record, can be null if the request is for a creation.", example="1")
    private Long id;

    @NotNull(message = "The room id cannot be null.")
    @Min(value = 0, message = "The room id cannot be negative")
    @Max(value = 9223372036854775807L, message = "The room id exceeds the maximum allowed")
    @Schema(description = "The room id for the record.", example="3")
    private Long id_room;

    @NotNull(message = "The people id cannot be null.")
    @Min(value = 0, message = "The people id cannot be negative")
    @Max(value = 9223372036854775807L, message = "The people id exceeds the maximum allowed")
    @Schema(description = "The people id for the record.", example="5")
    private Long id_people;

    @NotNull(message = "The date and time cannot be null.")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "America/Argentina/Buenos_Aires")
    @Schema(description = "Reservation start date and time", example = "2025-10-18 11:00:00")
    private LocalDateTime date_hour_start;

    @NotNull(message = "The date and time cannot be null.")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "America/Argentina/Buenos_Aires")
    @Schema(description = "Reservation end date and time", example = "2025-10-18 12:00:00")
    private LocalDateTime date_hour_end;

    @NotNull(message = "The date and time cannot be null.")
    @Schema(description = "Ids of the articles for the reserved room.", example="[1,2,3]")
    private List<Long> ids_articles;
    
}