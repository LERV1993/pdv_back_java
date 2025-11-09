package com.pdv.project.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Schema(description = "Login request data containing user credentials.")
public class LoginRequestDTO {

    @Schema(
        description = "User email used as username for authentication.",
        example = "admin@reservas.com"
    )
    @NotBlank(message = "Username (email) is required.")
    @Email(message = "Username must be a valid email address.")
    private String username;

    @Schema(
        description = "User password for authentication.",
        example = "admin123"
    )
    @NotBlank(message = "Password is required.")
    @Size(min = 6, max = 50, message = "Password must be between 6 and 50 characters.")
    private String password;
}
