package com.pdv.project.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pdv.project.dto.request.LoginRequestDTO;
import com.pdv.project.dto.response.AuthResponseDTO;
import com.pdv.project.dto.response.ErrorResponseDTO;
import com.pdv.project.security.UserDetailsServiceImpl;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final UserDetailsServiceImpl userDetailsServ;

    @Operation(
        summary = "User login",
        description = "Authenticates a user and returns a JWT token if credentials are valid.",
        requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
            required = true,
            content = @Content(
                schema = @Schema(implementation = LoginRequestDTO.class),
                examples = @ExampleObject(
                    name = "Example login",
                    summary = "Login example",
                    value = "{ \"username\": \"admin@reservas.com\", \"password\": \"admin123\" }"
                )
            )
        )
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Successful authentication",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = AuthResponseDTO.class),
                    examples = @ExampleObject( 
                        name = "Example response",
                        summary = "Response login example",    
                        value ="{\n"
                        + "  \"username\": \"admin@reservas.com\",\n"
                        + "  \"message\": \"Authentication successful\",\n"
                        + "  \"jwt\": \"eyJhbGciOiJIUzM4NCJ9.eyJlbWFpbCI6ImFkbWluQHJlc2VydmFzLmNvbSIsInN1YiI6ImFkbWluQHJlc2VydmFzLmNvbSIsImlhdCI6MTc2MjY1NDg1MywiZXhwIjoxNzYyNjU4NDUzLCJhdXRob3JpdGllcyI6IkFETUlOIn0.LRd36jaF_7Gmnf0XQWDNSQNCygsx7F_vDJENJuX_v6pVgurOq2P_jt0dPbqmbHg_\"\n"
                        + "}"
                    )
            )
        ),
        @ApiResponse(
            responseCode = "400",
            description = "Invalid credentials or malformed request",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = ErrorResponseDTO.class),
                    examples = @ExampleObject(
                        name = "Example error",
                        summary = "Error login example", 
                        value = "{\n"
                        + "  \"error\": \"Invalid username or password\",\n"
                        + "  \"status\": 400,\n"
                        + "  \"timestamp\": \"2025-11-08T21:16:00\"\n"
                        + "}"
                        )
            )
        )
    })
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid LoginRequestDTO request){

        try{
            return ResponseEntity.ok(userDetailsServ.authenticateUser(request));
        }catch(UsernameNotFoundException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }catch(BadCredentialsException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

    }
    
}
