package com.pdv.project.dto.common;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Schema(description = "Base data structure for other DTOs with id and name")
public abstract class BaseDTO {

    @Schema(description = "Unique identifier assigned to the record", example = "1")
    private Long id;

    @Schema(description = "The name corresponding to the record", example = "Rafael Di Zeo")
    private String name;
    
}
