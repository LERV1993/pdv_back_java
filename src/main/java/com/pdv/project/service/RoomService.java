package com.pdv.project.service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.pdv.project.dto.request.RoomRequestDTO;
import com.pdv.project.dto.response.RoomResponseDTO;
import com.pdv.project.entity.RoomEntity;
import com.pdv.project.repository.RoomsRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class RoomService {

    private final RoomsRepository repository;

    public List<RoomResponseDTO> getRooms() {

        List<RoomEntity> roomEntityList = this.repository.findAll();

        return roomEntityList.stream()
                .map(RoomResponseDTO::fromEntity)
                .filter(Objects::nonNull)
                .toList();

    }

    @Transactional
    public RoomResponseDTO addEditRooms(RoomRequestDTO request){

        if(request == null){
            log.info("Rooms Service - Add Rooms: request null.");
            throw new IllegalArgumentException("The request cannot be null.");
        }

        RoomEntity entity = RoomEntity.fromRequest(request);
        RoomEntity entitySaved = this.repository.save(entity);


        return RoomResponseDTO.fromEntity(entitySaved);

    }    

    @Transactional
    public RoomEntity deleteRooms(Long id) {

        if(id == null){
            log.info("Rooms Service - Delete Rooms: id null.");
            throw new IllegalArgumentException("To delete a record the ID cannot be null.");
        }

        Optional<RoomEntity> entity = this.repository.findById(id);

        if (entity.isPresent()) {
            repository.deleteById(id);
            return entity.get(); 
        }

        log.info("Rooms Service - Delete Rooms: The record for id {}, no exist.", id);
        throw new IllegalArgumentException("The request ID does not match any room."); 
    }

}