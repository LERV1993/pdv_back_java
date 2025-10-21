package com.pdv.project.entity;

import com.pdv.project.dto.request.ArticleRequestDTO;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "articles")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ArticleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "article_name", nullable = false)
    private String name;

    @Column(name = "available", nullable = false)
    private Boolean available;

    public static ArticleEntity fromRequest(ArticleRequestDTO request){

        if(request == null){
            return null;
        }

        return ArticleEntity.builder()
        .id(request.getId())
        .name(request.getName())
        .available(request.getAvailable())
        .build();

    }

}