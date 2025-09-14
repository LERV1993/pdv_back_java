package com.pdv.project.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pdv.project.service.TestService;

import lombok.RequiredArgsConstructor;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;


@RequestMapping("/test")
@RestController
@RequiredArgsConstructor
public class TestController {

    private final TestService testService;

    @GetMapping
    public ResponseEntity<Map<String,Object>> getMethod() {
        return ResponseEntity.ok(testService.returnValue("GET"));
    }
    
    @PostMapping
    public ResponseEntity<Map<String,Object>> postMethod() {
        return ResponseEntity.ok(testService.returnValue("POST"));
    }
    
}
