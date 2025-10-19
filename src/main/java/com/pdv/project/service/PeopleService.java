package com.pdv.project.service;

import java.util.List;
import java.util.Objects;

import org.springframework.stereotype.Service;

import com.pdv.project.dto.request.PeopleRequestDTO;
import com.pdv.project.dto.response.PeopleResponseDTO;
import com.pdv.project.entity.PeopleEntity;
import com.pdv.project.repository.PeopleRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class PeopleService {

    private final PeopleRepository repository;

    public List<PeopleResponseDTO> getPeople() {

        List<PeopleEntity> peopleEntityList = this.repository.findAll();

        return peopleEntityList.stream()
                .map(PeopleResponseDTO::fromEntity)
                .filter(Objects::nonNull)
                .toList();

    }

    @Transactional
    public PeopleResponseDTO addEditPeople(PeopleRequestDTO request){

        if(request == null){
            log.info("People Service - Add People: request null.");
            return null;
        }

        PeopleEntity entity = PeopleEntity.fromRequest(request);
        PeopleEntity entitySaved = this.repository.save(entity);


        return PeopleResponseDTO.fromEntity(entitySaved);

    }    

    @Transactional
    public boolean deletePeople(Long id) {

        if(id == null){
            log.info("People Service - Delete People: id null.");
            return false;
        }


        if (repository.existsById(id)) {
            repository.deleteById(id);
            return true; 
        }
        return false; 
    }


}
