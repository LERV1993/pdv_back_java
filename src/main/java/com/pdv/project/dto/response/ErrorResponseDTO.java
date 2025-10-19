package com.pdv.project.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Error response details")
public class ErrorResponseDTO {

    @Schema(description = "Error message", example = "Invalid data")
    private String error;

    @Schema(description = "HTTP status code")
    private int status;

    @Schema(description = "Timestamp of the error", example = "2025-09-17T10:16:00")
    private String timestamp;

}
