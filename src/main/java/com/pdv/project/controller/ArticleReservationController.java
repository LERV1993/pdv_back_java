package com.pdv.project.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pdv.project.dto.request.ArticlesAvailablesRequestDTO;
import com.pdv.project.dto.request.PeopleRequestDTO;
import com.pdv.project.dto.response.ArticleResponseDTO;
import com.pdv.project.dto.response.ErrorResponseDTO;
import com.pdv.project.dto.response.PeopleResponseDTO;
import com.pdv.project.service.ArticleReservationService;

import io.swagger.v3.oas.annotations.Operation;
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

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
@RequestMapping("/articles-reservation")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Articles Reservation controller", description = "Endpoint for actions related to the administration and consultation of articles in reservations.")
public class ArticleReservationController {

    private final ArticleReservationService service; 
    
    @Operation(
        summary = "Get articles availables from date selected",
        description = "Receive date of interest to obtain articles data."
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "200",
            description = "Data received and saved successfully.",
            content = @Content(
                mediaType = "application/json",
                array = @ArraySchema(schema = @Schema(implementation = ArticleResponseDTO.class)),
                examples = @ExampleObject(
                value = "[{ \"id\": 1, \"name\": \"Gadnic 8000 lumen projector\", \"available\": false  },{ \"id\": 2, \"name\": \"Kanji 700 lumen projector\", \"available\": true  }]")
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
        description = "The format of the data must be YYYY-MM-DD HH:MM:SS. It should be noted that the database stores information in UTC format and queries must be made using GMT-3 Buenos Aires, Argentina time.",
        required = true,
        content = @Content(
            mediaType = "application/json",
            schema = @Schema(implementation = ArticlesAvailablesRequestDTO.class)
        )
    )
    @PostMapping("/available")
    public ResponseEntity<List<ArticleResponseDTO>> postMethodName(@RequestBody @Valid ArticlesAvailablesRequestDTO request) {
        return ResponseEntity.ok(this.service.getArticlesAvailablesForReservation(request));
    }

}
