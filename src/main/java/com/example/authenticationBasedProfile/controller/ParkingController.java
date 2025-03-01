package com.example.authenticationBasedProfile.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/parking")
public class ParkingController {

    @GetMapping
    public ResponseEntity<String> getParkingData(Authentication authentication) {

    return ResponseEntity.ok("Acesso permitido aos dados de estacionamento.");

    }
}

