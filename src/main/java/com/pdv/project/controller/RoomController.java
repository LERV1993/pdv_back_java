package com.pdv.project.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pdv.project.dto.request.RoomRequestDTO;
import com.pdv.project.dto.response.ErrorResponseDTO;
import com.pdv.project.dto.response.RoomResponseDTO;
import com.pdv.project.service.RoomService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/rooms")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Rooms controller", description = "Endpoint for actions related to the administration and consultation of Rooms.")
public class RoomController {

    private final RoomService service;

    @Operation(
        summary = "Get rooms data in the system",
        description = "Return list of rooms registered in the system."
    )
    @ApiResponses({
    @ApiResponse(
            responseCode = "200",
            description = "Data returned successfully.",
            content = @Content(
                mediaType = "application/json",
                array = @ArraySchema(schema = @Schema(implementation = RoomResponseDTO.class)),
                examples = @ExampleObject(
                value = "[{ \"id\": 1, \"name\": \"Sala A3\", \"capacity\": 15  }]")
            )
        ),
        @ApiResponse(
            responseCode = "400",
            description = "Bad request. The request could not be processed.",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = ErrorResponseDTO.class),
                examples = @ExampleObject(
                value = "{ \"error\": \"Invalid data\", \"status\": \"400\", \"timestamp\": \"2025-09-17T10:16:00\"  }")
            )
        ),
        @ApiResponse(
            responseCode = "500",
            description = "Internal server error. The request was not processed.",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = ErrorResponseDTO.class),
                examples = @ExampleObject(
                value = "{ \"error\": \"Server Error\", \"status\": \"500\", \"timestamp\": \"2025-09-17T10:16:00\" }")
            )
        )
    })    
    @GetMapping
    public ResponseEntity<?> getRooms() {

        try {

            return ResponseEntity.ok(this.service.getRooms());

        } catch (Exception e) {

            ErrorResponseDTO errorResponse = ErrorResponseDTO.builder()
                    .error(e.getMessage())
                    .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                    .timestamp(java.time.LocalDateTime.now().toString())
                    .build();

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);

        }

    }

    @Operation(
        summary = "Update data from room in the system",
        description = "Receive information to update room in the system."
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "200",
            description = "Data received and saved successfully.",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = RoomResponseDTO.class),
                examples = @ExampleObject(
                value = "{ \"id\": 1, \"name\": \"Sala A3 - edited\", \"capacity\": 20  }")
            )
        ),
        @ApiResponse(
            responseCode = "400",
            description = "Bad request. The data could not be processed or saved.",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = ErrorResponseDTO.class),
                examples = @ExampleObject(
                value = "{ \"error\": \"Invalid data\", \"status\": \"400\", \"timestamp\": \"2025-09-17T10:16:00\"  }")
            )
        ),
        @ApiResponse(
            responseCode = "500",
            description = "Internal server error. The request was not processed.",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = ErrorResponseDTO.class),
                examples = @ExampleObject(
                value = "{ \"error\": \"Server Error\", \"status\": \"500\", \"timestamp\": \"2025-09-17T10:16:00\" }")
            )
        )
    })
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
        description = "For edit room the id is mandatory",
        required = true,
        content = @Content(
            mediaType = "application/json",
            schema = @Schema(implementation = RoomRequestDTO.class),
            examples = @ExampleObject(
            value = "{ \"id\": 1, \"name\": \"Sala A3 - edited\", \"capacity\": 20  }")
        )
    )
    @PutMapping
    public ResponseEntity<?> editRooms(@Valid @RequestBody RoomRequestDTO request) {

        try {

            if(request.getId() == null){

                ErrorResponseDTO errorResponse = ErrorResponseDTO.builder()
                    .error("The id is mandatory to edit a room record.")
                    .status(HttpStatus.BAD_REQUEST.value())
                    .timestamp(java.time.LocalDateTime.now().toString())
                    .build();

                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);

            }

            return ResponseEntity.ok(this.service.addEditRooms(request));

        } catch (Exception e) {

            log.info("Error: {}", e.getCause());

            ErrorResponseDTO errorResponse = ErrorResponseDTO.builder()
                    .error(e.getMessage())
                    .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                    .timestamp(java.time.LocalDateTime.now().toString())
                    .build();

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }

    }


    @Operation(
        summary = "Post data from new room in the system",
        description = "Receive information about a new room to register in the system."
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "200",
            description = "Data received and stored successfully.",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = RoomResponseDTO.class),
                examples = @ExampleObject(
                value = "{ \"id\": 2, \"name\": \"Sala A3\", \"capacity\": 32  }")
            )
        ),
        @ApiResponse(
            responseCode = "400",
            description = "Bad request. The data could not be processed or saved.",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = ErrorResponseDTO.class),
                examples = @ExampleObject(
                value = "{ \"error\": \"Invalid data\", \"status\": \"400\", \"timestamp\": \"2025-09-17T10:16:00\"  }")
            )
        ),
        @ApiResponse(
            responseCode = "500",
            description = "Internal server error. The request was not processed.",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = ErrorResponseDTO.class),
                examples = @ExampleObject(
                value = "{ \"error\": \"Server Error\", \"status\": \"500\", \"timestamp\": \"2025-09-17T10:16:00\" }")
            )
        )
    })
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
        description = "For create record the id must be null",
        required = true,
        content = @Content(
            mediaType = "application/json",
            schema = @Schema(implementation = RoomRequestDTO.class),
            examples = @ExampleObject(
            value = "{ \"id\": null, \"name\": \"Sala A3\", \"capacity\": 32  }")
        )
    )
    @PostMapping
    public ResponseEntity<?> addRooms(@Valid @RequestBody RoomRequestDTO request) {

        try {

            if(request.getId() != null){

                ErrorResponseDTO errorResponse = ErrorResponseDTO.builder()
                    .error("To create a record the id must be null.")
                    .status(HttpStatus.BAD_REQUEST.value())
                    .timestamp(java.time.LocalDateTime.now().toString())
                    .build();

                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);

            }

            return ResponseEntity.ok(this.service.addEditRooms(request));

        } catch (Exception e) {

            log.info("Error: {}", e.getCause());

            ErrorResponseDTO errorResponse = ErrorResponseDTO.builder()
                    .error(e.getMessage())
                    .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                    .timestamp(java.time.LocalDateTime.now().toString())
                    .build();

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }

    }

    @Operation(
    summary = "Delete a room by ID",
    description = "Deletes the room record associated with the given ID."
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Room deleted successfully.",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = RoomResponseDTO.class),
                examples = @ExampleObject(
                value = "{ \"id\": 1, \"name\": \"Sala A3\", \"capacity\": 32  }")
            )
        ),
        @ApiResponse(
            responseCode = "400",
            description = "Invalid or missing ID parameter.",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = ErrorResponseDTO.class),
                examples = @ExampleObject(
                    value = "{ \"error\": \"The id is mandatory to delete a Rooms record.\", \"status\": 400, \"timestamp\": \"2025-10-19T02:39:28\" }"
                )
            )
        ),
        @ApiResponse(
            responseCode = "500",
            description = "Unexpected server error.",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = ErrorResponseDTO.class),
                examples = @ExampleObject(
                    value = "{ \"error\": \"Internal server error.\", \"status\": 500, \"timestamp\": \"2025-10-19T02:39:28\" }"
                )
            )
        )
    })
    @Parameter(
        name = "id",
        description = "The unique ID of the room to delete.",
        required = true,
        example = "1"
    )    
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteRooms(@PathVariable Long id){

        if(id == null){

            ErrorResponseDTO errorResponse = ErrorResponseDTO.builder()
                .error("The id is mandatory to delete a room record.")
                .status(HttpStatus.BAD_REQUEST.value())
                .timestamp(java.time.LocalDateTime.now().toString())
                .build();

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);

        }

        try{

            return ResponseEntity.ok(this.service.deleteRooms(id));

        }catch(Exception e){

            ErrorResponseDTO errorResponse = ErrorResponseDTO.builder()
                    .error(e.getMessage())
                    .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                    .timestamp(java.time.LocalDateTime.now().toString())
                    .build();

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
            
        }

    }

}