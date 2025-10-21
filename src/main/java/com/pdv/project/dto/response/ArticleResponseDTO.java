package com.pdv.project.dto.response;

import com.pdv.project.dto.common.BaseDTO;
import com.pdv.project.entity.ArticleEntity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@SuperBuilder
@Schema(description = "Items available for loan for use in reserved rooms")
public class ArticleResponseDTO extends BaseDTO {

    @Schema(description = "Describes whether the item is available", example = "false")
    private boolean available;

    public static ArticleResponseDTO fromEntity(ArticleEntity entity){

        if(entity == null){
            return null;
        }

        return ArticleResponseDTO.builder()
        .id(entity.getId())
        .name(entity.getName())
        .available(entity.getAvailable())
        .build();
    }
    
}