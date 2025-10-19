package com.pdv.project.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Item assigned to the reservation.")
public class ArticlesReservationsResponseDTO {
    
    @Schema(description = "Unique identifier assigned to the record", example = "10")
    private Long id;

    @Schema(description = "Unique identifier assigned to the article corresponding to reservation", example = "6")
    private Long id_article;

    @Schema(description = "Unique identifier assigned to the reservation", example = "8")
    private Long id_reservation;
    
}