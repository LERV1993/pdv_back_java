package com.pdv.project.dto.request;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Date to see availables articles.")
public class ArticlesAvailablesRequestDTO {

    @NotNull(message = "The date and time cannot be null.")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "America/Argentina/Buenos_Aires")
    @Schema(description = "Reservation start date and time", example = "2025-10-18 11:00:00")
    private LocalDateTime date;

}
