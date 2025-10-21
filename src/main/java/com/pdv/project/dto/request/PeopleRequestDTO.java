package com.pdv.project.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "New person request for regsitration or person record to edit with id.")
public class PeopleRequestDTO {

    @Min(value = 0, message = "The id cannot be negative")
    @Max(value = 9223372036854775807L, message = "The id exceeds the maximum allowed")
    @Schema(description = "The id for the record, can be null if the request is for a creation.", example="1")
    private Long id;

    @NotBlank(message = "The person name cannot be empty")
    @Size(min = 1, max = 200, message = "The name cannot be longer than 200 characters.")
    @Schema(description = "The person name.", example = "Rafael Di Zeo")
    private String name;

    @NotBlank(message = "The email is mandatory.")
    @Size(min = 1, max = 200, message = "The email cannot be longer than 200 characters.")
    @Email(message = "The field for email is not valid")
    @Schema(description = "The person email.", example = "bostero22@hotmail.com")
    private String email;
    
}