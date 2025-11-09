package com.pdv.project.shared.service;

import java.nio.charset.StandardCharsets;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pdv.project.config.RabbitMQConfig;
import com.pdv.project.dto.rabbitTemplate.ReservationTemplateDTO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class RabbitMQService {

    private final RabbitTemplate rabbitTemplate;
    private final ObjectMapper objectMapper;

    public void sendTemplate(ReservationTemplateDTO response) {

        try {

            String json = objectMapper.writeValueAsString(response);
            rabbitTemplate.convertAndSend(RabbitMQConfig.QUEUE_NAME, json.getBytes(StandardCharsets.UTF_8));
            log.info("Rabbit MQ Service - SendTemplate: Success");

        } catch (Exception e) {

            e.printStackTrace();
            log.info("Rabbit MQ Service - SendTemplate: Error {}", e.getMessage());

        }
    }

}
