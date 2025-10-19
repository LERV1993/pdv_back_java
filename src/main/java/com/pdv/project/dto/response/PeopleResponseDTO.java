package com.pdv.project.dto.response;

import com.pdv.project.dto.common.BaseDTO;
import com.pdv.project.entity.PeopleEntity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@SuperBuilder
@Schema(description = "Person making the room reservation.")
public class PeopleResponseDTO extends BaseDTO {
    
    @Schema(description = "Email address of the person", example = "bostero22@hotmail.com")
    private String email;

    public static PeopleResponseDTO fromEntity(PeopleEntity entity){

        if(entity == null){
            return null;
        }

        return PeopleResponseDTO.builder()
        .id(entity.getId())
        .name(entity.getName())
        .email(entity.getEmail())
        .build();
    }

}