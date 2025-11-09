package com.pdv.project.dto.response;

import com.pdv.project.dto.common.BaseDTO;
import com.pdv.project.entity.RoomEntity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@SuperBuilder
@Schema(description = "Rooms available for reservation by people in the system.")
public class RoomResponseDTO extends BaseDTO {

    @Schema(description = "Room capacity.", example = "15")
    private int capacity;

    public static RoomResponseDTO fromEntity(RoomEntity entity) {
        if (entity == null) {
            throw new IllegalArgumentException("The entity cannot be null.");
        }

        return RoomResponseDTO.builder()
                .id(entity.getId())
                .name(entity.getName())
                .capacity(entity.getCapacity())
                .build();
    }

}