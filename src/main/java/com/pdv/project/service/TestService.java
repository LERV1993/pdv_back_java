package com.pdv.project.service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

@Service
public class TestService {


    public Map<String, Object> returnValue(String value){

        Map<String, Object> response = new HashMap<>();
        LocalDateTime now = LocalDateTime.now();


        response.put("method", value);
        response.put("time", now);
        response.put("status", "ok");
        response.put("message", "hello!");

        return response;

    }
    
}
