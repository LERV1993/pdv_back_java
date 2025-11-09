package com.pdv.project.dto.rabbitTemplate;

import java.util.List;

import com.pdv.project.dto.response.ArticleResponseDTO;
import com.pdv.project.dto.response.ReservationDetailResponseDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReservationTemplateDTO {

    private Long reservation_id;
    private String room_name;
    private String people_email;
    private List<String> articles;
    private String date_hour_start;
    private String date_hour_end;

    public static ReservationTemplateDTO fromDetails(ReservationDetailResponseDTO details) {

        if (details == null) {
            return null;
        }

        return ReservationTemplateDTO.builder()
                .reservation_id(details.getId())
                .room_name(details.getRoom().getName())
                .people_email(details.getPeople().getEmail())
                .articles(details.getArticles().stream().map(ArticleResponseDTO::getName).toList())
                .date_hour_start(details.getDate_hour_start())
                .date_hour_end(details.getDate_hour_end())
                .build();

    }

}