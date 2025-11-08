package com.pdv.project.service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.pdv.project.dto.request.ArticleRequestDTO;
import com.pdv.project.dto.response.ArticleResponseDTO;
import com.pdv.project.entity.ArticleEntity;
import com.pdv.project.repository.ArticleRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class ArticleService {

    private final ArticleRepository repository;

    public List<ArticleResponseDTO> getArticles() {

        List<ArticleEntity> articleEntityList = this.repository.findAll();

        return articleEntityList.stream()
                .map(ArticleResponseDTO::fromEntity)
                .filter(Objects::nonNull)
                .toList();

    }

    public List<ArticleResponseDTO> getArticlesAvailables(){

        List<ArticleEntity> articleEntityList = this.repository.findByAvailableTrue();

        return articleEntityList.stream()
                .map(ArticleResponseDTO::fromEntity)
                .filter(Objects::nonNull)
                .toList();

    }

    public List<ArticleResponseDTO> getArticlesNotAvailables(){

        List<ArticleEntity> articleEntityList = this.repository.findByAvailableFalse();

        return articleEntityList.stream()
                .map(ArticleResponseDTO::fromEntity)
                .filter(Objects::nonNull)
                .toList();

    }

    @Transactional
    public ArticleResponseDTO addEditArticles(ArticleRequestDTO request){

        if(request == null){
            log.info("Articles Service - Add Articles: request null.");
            return null;
        }

        ArticleEntity entity = ArticleEntity.fromRequest(request);
        ArticleEntity entitySaved = this.repository.save(entity);


        return ArticleResponseDTO.fromEntity(entitySaved);

    }    

    @Transactional
    public ArticleEntity deleteArticles(Long id) {

        if(id == null){
            log.info("Article Service - Delete Article: id null.");
            return null;
        }

        Optional<ArticleEntity> entity = this.repository.findById(id);

        if (entity.isPresent()) {
            repository.deleteById(id);
            return entity.get(); 
        }

        log.info("Article Service - Delete Article: The record for id {}, no exist.", id);
        return null; 
    }

}