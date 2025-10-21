package com.pdv.project.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "New room request for regsitration or room record to edit with id.")
public class RoomRequestDTO {
    
    @Min(value = 0, message = "The id cannot be negative")
    @Max(value = 9223372036854775807L, message = "The id exceeds the maximum allowed")
    @Schema(description = "The id for the record, can be null if the request is for a creation.", example="18")
    private Long id;

    @NotBlank(message = "The room name cannot be empty")
    @Size(min = 1, max = 200, message = "The name cannot be longer than 200 characters.")
    @Schema(description = "The room name.", example = "Room A3")
    private String name;

    @NotNull(message = "The room capacity cannot be null.")
    @Min(value = 0, message = "The capacity cannot be negative.")
    @Max(value = 200, message = "The capacity exceeds the maximum allowed")
    @Schema(description = "The capacity of persons for the room.", example="20")
    private int capacity;

}