package com.pdv.project.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pdv.project.dto.request.ReservationRequestDTO;
import com.pdv.project.dto.response.ErrorResponseDTO;
import com.pdv.project.dto.response.ReservationDetailResponseDTO;
import com.pdv.project.dto.response.ReservationResponseDTO;
import com.pdv.project.entity.ReservationEntity;
import com.pdv.project.service.ReservationsService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@RestController
@RequestMapping("/reservation")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Reservation controller", description = "Endpoint for actions related to the administration and consultation of reservations.")
public class ReservationController {

    private final ReservationsService service;

    @Operation(
        summary = "Get reservations data in the system",
        description = "Return list of reservations registered in the system."
    )
    @ApiResponses({
    @ApiResponse(
            responseCode = "200",
            description = "Data returned successfully.",
            content = @Content(
                mediaType = "application/json",
                array = @ArraySchema(schema = @Schema(implementation = ReservationResponseDTO.class))
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
    public ResponseEntity<?> getReservations() {

        try {

            return ResponseEntity.ok(this.service.getReservations());

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
        summary = "Update data from reservation in the system",
        description = "Receive information to update reservation in the system."
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "200",
            description = "Data received and saved successfully.",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = ReservationResponseDTO.class)
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
        description = "For edit reservation the id is mandatory",
        required = true,
        content = @Content(
            mediaType = "application/json",
            schema = @Schema(implementation = ReservationRequestDTO.class)
        )
    )
    @PutMapping
    public ResponseEntity<?> editReservation(@Valid @RequestBody ReservationRequestDTO request) {

        try {

            if (request.getId() == null) {

                ErrorResponseDTO errorResponse = ErrorResponseDTO.builder()
                        .error("The id is mandatory to edit a reservation record.")
                        .status(HttpStatus.BAD_REQUEST.value())
                        .timestamp(java.time.LocalDateTime.now().toString())
                        .build();

                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);

            }

            ReservationResponseDTO response = this.service.addEditReservations(request);

            if(response != null){
                return ResponseEntity.ok(response);
            }

            ErrorResponseDTO errorResponse = ErrorResponseDTO.builder()
                    .error("It was not possible to edit the requested record.")
                    .status(HttpStatus.BAD_REQUEST.value())
                    .timestamp(java.time.LocalDateTime.now().toString())
                    .build();

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);

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
        summary = "Post data from new reservation in the system",
        description = "Receive information about a new reservation to register in the system."
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "200",
            description = "Data received and stored successfully.",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = ReservationResponseDTO.class)
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
            schema = @Schema(implementation = ReservationResponseDTO.class),
            examples = @ExampleObject(
            value = "{ \"id\": null, \"id_room\": 3,\"id_people\": 5,\"expected_people\": 10 ,\"date_hour_start\": \"2025-10-18 11:00:00\",\"date_hour_end\": \"2025-10-18 12:00:00\",\"ids_articles\": [1,2,3]}")
        )
    )
    @PostMapping
    public ResponseEntity<?> addReservation(@Valid @RequestBody ReservationRequestDTO request) {

        try {

            if (request.getId() != null) {

                ErrorResponseDTO errorResponse = ErrorResponseDTO.builder()
                        .error("To create a record the id must be null.")
                        .status(HttpStatus.BAD_REQUEST.value())
                        .timestamp(java.time.LocalDateTime.now().toString())
                        .build();

                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);

            }

            ReservationResponseDTO response = this.service.addEditReservations(request);

            if(response != null){
                return ResponseEntity.ok(response);
            }

            ErrorResponseDTO errorResponse = ErrorResponseDTO.builder()
                    .error("We were unable to create the reservation. Please review your request.")
                    .status(HttpStatus.BAD_REQUEST.value())
                    .timestamp(java.time.LocalDateTime.now().toString())
                    .build();

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
            

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
        summary = "Get reservation details by ID",
        description = "Returns the full details of a reservation, including associated person, room, and articles."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Reservation details retrieved successfully",
            content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = ReservationDetailResponseDTO.class))),
        @ApiResponse(responseCode = "404", description = "Reservation not found with the specified ID",
            content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = ErrorResponseDTO.class))),
        @ApiResponse(responseCode = "500", description = "Internal server error",
            content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = ErrorResponseDTO.class)))
    })
    @GetMapping("/reservation-details/{id}")
    public ResponseEntity<?> getReservationDetails(
        @Parameter(description = "Reservation ID to retrieve", example = "1")
        @PathVariable Long id
    ) {
        try {
            return ResponseEntity.ok(this.service.getReservationDetails(id));
        } catch (EntityNotFoundException e) {
            ErrorResponseDTO errorResponse = ErrorResponseDTO.builder()
                    .error(e.getMessage())
                    .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                    .timestamp(java.time.LocalDateTime.now().toString())
                    .build();

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    @Operation(
        summary = "Get all reservation details by people id",
        description = "Returns the list of all reservations made by the person corresponding to the specified ID, including information related to people, rooms, and items."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Reservations retrieved successfully",
            content = @Content(mediaType = "application/json",
                array = @ArraySchema(schema = @Schema(implementation = ReservationDetailResponseDTO.class)))),
        @ApiResponse(responseCode = "500", description = "Internal server error",
            content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = ErrorResponseDTO.class)))
    })
    @GetMapping("/reservation-details-by-person/{id}")
    public ResponseEntity<?> getAllReservationDetailsByPersonId(@PathVariable Long id) {
        return ResponseEntity.ok(this.service.getReservationDetailsByPersonId(id));
    }


    @Operation(
        summary = "Get all reservation details",
        description = "Returns the list of all reservations, including related person, room, and articles information."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Reservations retrieved successfully",
            content = @Content(mediaType = "application/json",
                array = @ArraySchema(schema = @Schema(implementation = ReservationDetailResponseDTO.class)))),
        @ApiResponse(responseCode = "500", description = "Internal server error",
            content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = ErrorResponseDTO.class)))
    })
    @GetMapping("/get-all-reservation-details")
    public ResponseEntity<?> getAllReservationDetails() {
        return ResponseEntity.ok(this.service.getAllReservationDetails());
    }


    @Operation(
        summary = "Delete a reservation by ID",
        description = "Deletes the reservation record associated with the given ID."
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Person deleted successfully.",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = ReservationResponseDTO.class)
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
    public ResponseEntity<?> deleteReservation(@PathVariable Long id) {

        if (id == null) {

            ErrorResponseDTO errorResponse = ErrorResponseDTO.builder()
                    .error("The id is mandatory to delete a reservation record.")
                    .status(HttpStatus.BAD_REQUEST.value())
                    .timestamp(java.time.LocalDateTime.now().toString())
                    .build();

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);

        }

        try {   

            ReservationResponseDTO reservationDeleted = this.service.deleteReservations(id);

            if(reservationDeleted != null){
                return ResponseEntity.ok(reservationDeleted);
            }

            ErrorResponseDTO errorResponse = ErrorResponseDTO.builder()
                    .error("The record cannot be deleted, please review the reqeust.")
                    .status(HttpStatus.BAD_REQUEST.value())
                    .timestamp(java.time.LocalDateTime.now().toString())
                    .build();

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
            

        } catch (Exception e) {

            ErrorResponseDTO errorResponse = ErrorResponseDTO.builder()
                    .error(e.getMessage())
                    .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                    .timestamp(java.time.LocalDateTime.now().toString())
                    .build();

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);

        }

    }

}