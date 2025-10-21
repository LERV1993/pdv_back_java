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
public class ArticleRequestDTO {
    
    @Min(value = 0, message = "The id cannot be negative")
    @Max(value = 9223372036854775807L, message = "The id exceeds the maximum allowed")
    @Schema(description = "The id for the record, can be null if the request is for a creation.", example="18")
    private Long id;

    @NotBlank(message = "The article name cannot be empty")
    @Size(min = 1, max = 200, message = "The name cannot be longer than 200 characters.")
    @Schema(description = "The article name.", example = "Notebook Dell small")
    private String name;

    @NotNull(message = "Availability cannot be null.")
    @Schema(description = "Describe whether the item is available for use.", example = "false")
    private Boolean available;

}