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

import com.pdv.project.dto.request.ArticleRequestDTO;
import com.pdv.project.dto.response.ArticleResponseDTO;
import com.pdv.project.dto.response.ErrorResponseDTO;
import com.pdv.project.service.ArticlesService;

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
@RequestMapping("/articles")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Articles controller", description = "Endpoint for actions related to the administration and consultation of articles.")
public class ArticleController {

    private final ArticlesService service;

    @Operation(
        summary = "Get all articles data in the system",
        description = "Return list of all articles registered in the system."
    )
    @ApiResponses({
    @ApiResponse(
            responseCode = "200",
            description = "Data returned successfully.",
            content = @Content(
                mediaType = "application/json",
                array = @ArraySchema(schema = @Schema(implementation = ArticleResponseDTO.class)),
                examples = @ExampleObject(
                value = "[{ \"id\": 1, \"name\": \"Gadnic 8000 lumen projector\", \"available\": false  },{ \"id\": 2, \"name\": \"Kanji 700 lumen projector\", \"available\": true  }]")
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
    public ResponseEntity<?> getAllArticles() {

        try {

        return ResponseEntity.ok(this.service.getArticles());

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
        summary = "Update data from article in the system",
        description = "Receive information to update article in the system."
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "200",
            description = "Data received and saved successfully.",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = ArticleResponseDTO.class),
                examples = @ExampleObject(
                value = "{ \"id\": 18, \"name\": \"Notebook Dell small\", \"available\": false }")
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
        description = "For edit article the id is mandatory",
        required = true,
        content = @Content(
            mediaType = "application/json",
            schema = @Schema(implementation = ArticleRequestDTO.class)
        )
    )   
    @PutMapping
    public ResponseEntity<?> editArticles(@Valid @RequestBody ArticleRequestDTO request) {

        try {

            if(request.getId() == null){

                ErrorResponseDTO errorResponse = ErrorResponseDTO.builder()
                    .error("The id is mandatory to edit a articles record.")
                    .status(HttpStatus.BAD_REQUEST.value())
                    .timestamp(java.time.LocalDateTime.now().toString())
                    .build();

                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);

            }

            return ResponseEntity.ok(this.service.addEditArticles(request));

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
        summary = "Post data from new article in the system",
        description = "Receive information about a new article to register in the system."
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "200",
            description = "Data received and stored successfully.",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = ArticleResponseDTO.class),
                examples = @ExampleObject(
                value = "{ \"id\": 30, \"name\": \"Notebook Dell small\", \"available\": true }")
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
            schema = @Schema(implementation = ArticleResponseDTO.class),
            examples = @ExampleObject(
            value = "{ \"id\": null, \"name\": \"Notebook Dell small\", \"available\": true }")
        )
    )   
    @PostMapping
    public ResponseEntity<?> addArticle(@Valid @RequestBody ArticleRequestDTO request) {

        try {

            if(request.getId() != null){

                ErrorResponseDTO errorResponse = ErrorResponseDTO.builder()
                    .error("To create a record the id must be null.")
                    .status(HttpStatus.BAD_REQUEST.value())
                    .timestamp(java.time.LocalDateTime.now().toString())
                    .build();

                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);

            }

            return ResponseEntity.ok(this.service.addEditArticles(request));

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
        summary = "Get all articles available in the system.",
        description = "Return list of all articles availables registered in the system."
    )
    @ApiResponses({
    @ApiResponse(
            responseCode = "200",
            description = "Data returned successfully.",
            content = @Content(
                mediaType = "application/json",
                array = @ArraySchema(schema = @Schema(implementation = ArticleResponseDTO.class)),
                examples = @ExampleObject(
                value = "[{ \"id\": 1, \"name\": \"Gadnic 8000 lumen projector\", \"available\": true  },{ \"id\": 2, \"name\": \"Kanji 700 lumen projector\", \"available\": true  }]")
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
    @GetMapping("/available")
    public ResponseEntity<?> getArticlesAvailables() {

        try {

        return ResponseEntity.ok(this.service.getArticlesAvailables());

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
        summary = "Get all articles that are not available in the system.",
        description = "Return list of all articles availables registered in the system."
    )
    @ApiResponses({
    @ApiResponse(
            responseCode = "200",
            description = "Data returned successfully.",
            content = @Content(
                mediaType = "application/json",
                array = @ArraySchema(schema = @Schema(implementation = ArticleResponseDTO.class)),
                examples = @ExampleObject(
                value = "[{ \"id\": 1, \"name\": \"Gadnic 8000 lumen projector\", \"available\": false  },{ \"id\": 2, \"name\": \"Kanji 700 lumen projector\", \"available\": false  }]")
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
    @GetMapping("/not-available")
    public ResponseEntity<?> getArticlesNotAvailables() {

        try {

        return ResponseEntity.ok(this.service.getArticlesNotAvailables());

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
    summary = "Delete a article by ID",
    description = "Deletes the article record associated with the given ID."
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Article deleted successfully.",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = ArticleResponseDTO.class),
                examples = @ExampleObject(
                    value = "{ \"id\": 1, \"name\": \"Notebook Dell small\", \"available\": true }"
                )
            )
        ),
        @ApiResponse(
            responseCode = "400",
            description = "Invalid or missing ID parameter.",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = ErrorResponseDTO.class),
                examples = @ExampleObject(
                    value = "{ \"error\": \"The id is mandatory to delete a article record.\", \"status\": 400, \"timestamp\": \"2025-10-19T02:39:28\" }"
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
        description = "The unique ID of the article to delete.",
        required = true,
        example = "1"
    )    
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteArticles(@PathVariable Long id){

        if(id == null){

            ErrorResponseDTO errorResponse = ErrorResponseDTO.builder()
                .error("The id is mandatory to delete a articles record.")
                .status(HttpStatus.BAD_REQUEST.value())
                .timestamp(java.time.LocalDateTime.now().toString())
                .build();

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);

        }

        try{

            return ResponseEntity.ok(this.service.deleteArticles(id));

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
