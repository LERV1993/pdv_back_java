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
public class ArticlesService {

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

    /**
     * Disables (sets availability to false) all articles corresponding to the provided IDs.
     *
     * @param ids List of IDs corresponding to the articles to disable.
     * @return {@code true} if all articles were successfully disabled; {@code false} otherwise.
     */
    public boolean disableArticles(List<Long> ids) {

        if (ids.isEmpty()) {
            log.info("Articles Service - Disable Articles: No IDs provided for disabling articles.");
            return true;
        }

        List<ArticleEntity> listEntity = this.repository.findAllById(ids);

        if (listEntity.size() != ids.size()) {
            log.info("Articles Service - Disable Articles: Some provided IDs do not correspond to any record.");
            return false;
        }

        List<ArticleEntity> anyUnavailable = listEntity.stream()
                .filter(article -> !article.getAvailable())
                .toList();

        if(!anyUnavailable.isEmpty()){
            log.info("Articles Services - Disable Articles: Any of articles selected is not available.");
            return false;
        }   
        
        int rowsModified = this.repository.setAvailableFalseForIds(ids);

        boolean success = rowsModified == ids.size();
        if (!success) {
            log.info("Articles Service - Disable Articles: Expected to modify {} rows, but {} were modified.", ids.size(), rowsModified);
        }

        return success;

    }

}