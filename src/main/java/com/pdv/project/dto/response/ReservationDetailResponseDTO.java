package com.pdv.project.dto.response;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(
    description = "Reservation record with details.",
    example = """
    {
      "id": 1,
      "room": {
        "id": 3,
        "name": "Sala A",
        "capacity": 10
      },
      "people": {
        "id": 5,
        "name": "Juan",
        "email": "juan.perez@example.com"
      },
      "date_hour_start": "2025-10-18 11:00:00",
      "date_hour_end": "2025-10-18 12:00:00",
      "articles": [
        {
          "id": 7,
          "name": "Proyector",
          "available": true
        },
        {
          "id": 8,
          "name": "Pizarra",
          "available": true
        }
      ]
    }
    """
)
public class ReservationDetailResponseDTO {

    @Schema(description = "Unique identifier assigned to the record", example = "1")
    private Long id;

    @Schema(description = "Room for reservation")
    private RoomResponseDTO room;

    @Schema(description = "Person making the reservation")
    private PeopleResponseDTO people;

    @Schema(description = "Date and time of reservation start.", example = "2025-10-18 11:00:00")
    private String date_hour_start;

    @Schema(description = "Date and time of reservation end.", example = "2025-10-18 12:00:00")
    private String date_hour_end;

    @Schema(description = "List of articles for reservation")
    private List<ArticleResponseDTO> articles;
}
