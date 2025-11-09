package com.pdv.project.controller;

import java.util.Map;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

import com.pdv.project.dto.request.PeopleRequestDTO;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@Tag(
    name = "Predictions Controller",
    description = """
        Endpoints related to predictive analytics of reservations, 
        including room occupancy probability, seasonal patterns, 
        trending resource usage, and system health monitoring.
        <br><br>
        All responses are proxied from the Python prediction microservice.
        """
)
public class PredictionsController {

    private final WebClient pythonWebClient;


    @Operation(
        summary = "Predicts the probability of a room being occupied",
        description = "Predicts the probability of a room being occupied between two dates using historical data from reservation_history."
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "OK. Prediction result.",
            content = @Content(
                mediaType = "application/json",
                examples = @ExampleObject(value = """
                    {
                      "room": "sala1",
                      "occupation_probability": 0.3,
                      "trend": "low"
                    }
                """)
            )
        ),
        @ApiResponse(
            responseCode = "401",
            description = "Bad request or unauthorized.",
            content = @Content(schema = @Schema(hidden = true))
        )
    })
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
        required = true,
        content = @Content(
            mediaType = "application/json",
            examples = @ExampleObject(
            value = "{\"date_hour_end\": \"2025-10-18T23:59:59\",\"date_hour_start\": \"2025-10-18T00:00:00\",\"room_name\": \"room_name\"}")
        )
    )
    @PostMapping("/occupancy")
    public ResponseEntity<Map<String, Object>> predict(@RequestBody Map<String, Object> body) {

        Map<String, Object> response = pythonWebClient.post()
                .uri("/api/v1/occupancy")
                .bodyValue(body)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<Map<String, Object>>() {
                })
                .block();

        return ResponseEntity.ok(response);
    }

    @Operation(
        summary = "Predictive ranking of room occupancy by weekday",
        description = "Fetches the predicted occupancy ranking for rooms per weekday from the Python service."
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "OK. Predicted occupancy ranking generated successfully.",
            content = @Content(
                mediaType = "application/json",
                examples = @ExampleObject(value = """
                    {
                      "monday": [
                        {"room": "Sala A1", "expected_occupancy": 0.85},
                        {"room": "Sala B2", "expected_occupancy": 0.73}
                      ],
                      "tuesday": [
                        {"room": "Sala A1", "expected_occupancy": 0.81},
                        {"room": "Sala B2", "expected_occupancy": 0.66}
                      ]
                    }
                """)
            )
        ),
        @ApiResponse(
            responseCode = "401",
            description = "Bad request or unauthorized.",
            content = @Content(schema = @Schema(hidden = true))
        )
    })
    @GetMapping("/occupancy-ranking")
    public ResponseEntity<Map<String, Object>> occupancyRanking() {

        Map<String, Object> response = pythonWebClient.get()
                .uri("/api/v1/occupancy-ranking")
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<Map<String, Object>>() {
                })
                .block();

        return ResponseEntity.ok(response);
    }

    @Operation(
        summary = "Analyze the usage trend of items (resources) in the reserves",
        description = "Returns item usage trends from reservation history. If data is limited, uses a simple comparison between the first and last records."
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Trends successfully detected.",
            content = @Content(
                mediaType = "application/json",
                examples = @ExampleObject(value = """
                    [
                      {
                        "article": "Proyector",
                        "trend": "+12.5%",
                        "trust": 0.85
                      },
                      {
                        "article": "Pizarra",
                        "trend": "-5.3%",
                        "trust": 0.68
                      }
                    ]
                """)
            )
        ),
        @ApiResponse(
            responseCode = "401",
            description = "Bad request or unauthorized.",
            content = @Content(schema = @Schema(hidden = true))
        )
    })
    @GetMapping("/trending-resources")
    public ResponseEntity<Map<String, Object>> trendingResources() {

        Map<String, Object> response = pythonWebClient.get()
                .uri("/api/v1/trending-resources")
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<Map<String, Object>>() {
                })
                .block();

        return ResponseEntity.ok(response);
    }

    @Operation(
        summary = "Analyze seasonal patterns of room usage",
        description = "Detect recurring occupancy patterns (days of the week with the most and least reservations) for each room."
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Seasonal patterns successfully detected.",
            content = @Content(
                mediaType = "application/json",
                examples = @ExampleObject(value = """
                    {
                      "sala1": {
                        "peak_day": "tuesday",
                        "low_day": "friday"
                      },
                      "sala2": {
                        "peak_day": "monday",
                        "low_day": "thursday"
                      }
                    }
                """)
            )
        ),
        @ApiResponse(
            responseCode = "401",
            description = "Bad request or unauthorized.",
            content = @Content(schema = @Schema(hidden = true))
        )
    })
    @GetMapping("/seasonal-patterns")
    public ResponseEntity<Map<String, Object>> seasonalPatterns() {

        Map<String, Object> response = pythonWebClient.get()
                .uri("/api/v1/seasonal-patterns")
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<Map<String, Object>>() {
                })
                .block();

        return ResponseEntity.ok(response);
    }

}
