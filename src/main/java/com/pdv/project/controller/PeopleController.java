package com.pdv.project.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pdv.project.dto.request.PeopleRequestDTO;
import com.pdv.project.dto.response.ErrorResponseDTO;
import com.pdv.project.dto.response.PeopleResponseDTO;
import com.pdv.project.entity.PeopleEntity;
import com.pdv.project.service.PeopleService;

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

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/people")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "People controller", description = "Endpoint for actions related to the administration and consultation of people.")
public class PeopleController {

    private final PeopleService service;

    @Operation(
        summary = "Get people data in the system",
        description = "Return list of people registered in the system."
    )
    @ApiResponses({
    @ApiResponse(
            responseCode = "200",
            description = "Data returned successfully.",
            content = @Content(
                mediaType = "application/json",
                array = @ArraySchema(schema = @Schema(implementation = PeopleResponseDTO.class))
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
    public ResponseEntity<?> getPeople() {

        try {

            return ResponseEntity.ok(this.service.getPeople());

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
        summary = "Update data from person in the system",
        description = "Receive information to update person in the system."
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "200",
            description = "Data received and saved successfully.",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = PeopleResponseDTO.class)
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
        description = "For edit people the id is mandatory",
        required = true,
        content = @Content(
            mediaType = "application/json",
            schema = @Schema(implementation = PeopleRequestDTO.class)
        )
    )
    @PutMapping
    public ResponseEntity<?> editPeople(@Valid @RequestBody PeopleRequestDTO request) {

        try {

            if(request.getId() == null){

                ErrorResponseDTO errorResponse = ErrorResponseDTO.builder()
                    .error("The id is mandatory to edit a people record.")
                    .status(HttpStatus.BAD_REQUEST.value())
                    .timestamp(java.time.LocalDateTime.now().toString())
                    .build();

                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);

            }

            return ResponseEntity.ok(this.service.addEditPeople(request));

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
    summary = "Delete a person by ID",
    description = "Deletes the person record associated with the given ID."
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Person deleted successfully.",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = PeopleResponseDTO.class)
            )
        ),
        @ApiResponse(
            responseCode = "400",
            description = "Invalid or missing ID parameter.",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = ErrorResponseDTO.class),
                examples = @ExampleObject(
                    value = "{ \"error\": \"The id is mandatory to delete a people record.\", \"status\": 400, \"timestamp\": \"2025-10-19T02:39:28\" }"
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
        description = "The unique ID of the person to delete.",
        required = true,
        example = "1"
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePeople(@PathVariable Long id){

        if(id == null){

            ErrorResponseDTO errorResponse = ErrorResponseDTO.builder()
                .error("The id is mandatory to delete a people record.")
                .status(HttpStatus.BAD_REQUEST.value())
                .timestamp(java.time.LocalDateTime.now().toString())
                .build();

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);

        }

        try{

            PeopleEntity entity = this.service.deletePeople(id);
            if(entity != null){
                return ResponseEntity.ok(entity);
            }
            
            ErrorResponseDTO errorResponse = ErrorResponseDTO.builder()
                .error("The ID does not match any record.")
                .status(HttpStatus.BAD_REQUEST.value())
                .timestamp(java.time.LocalDateTime.now().toString())
                .build();

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);

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