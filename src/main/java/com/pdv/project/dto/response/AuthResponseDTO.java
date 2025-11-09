package com.pdv.project.dto.response;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@JsonPropertyOrder({ "username", "message", "jwt" })
@Builder
@Schema(description = "Response returned after a successful authentication, containing user information and JWT token.")
public record AuthResponseDTO(

    @Schema(
        description = "Email or username of the authenticated user.",
        example = "admin@reservas.com"
    )
    String username,

    @Schema(
        description = "Message indicating the authentication result.",
        example = "Authentication successful"
    )
    String message,

    @Schema(
        description = "JWT token to be used in Authorization header for future requests.",
        example = "eyJhbGciOiJIUzM4NCJ9.eyJlbWFpbCI6ImFkbWluQHJlc2VydmFzLmNvbSIsInN1YiI6ImFkbWluQHJlc2VydmFzLmNvbSIsImlhdCI6MTc2MjY1NDg1MywiZXhwIjoxNzYyNjU4NDUzLCJhdXRob3JpdGllcyI6IkFETUlOIn0.LRd36jaF_7Gmnf0XQWDNSQNCygsx7F_vDJENJuX_v6pVgurOq2P_jt0dPbqmbHg_"
    )
    String jwt
) { }
