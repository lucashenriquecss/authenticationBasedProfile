package com.example.authenticationBasedProfile.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.authenticationBasedProfile.dto.AuthRequest;
import com.example.authenticationBasedProfile.dto.AuthResponse;
import com.example.authenticationBasedProfile.dto.SwitchProfileRequest;
import com.example.authenticationBasedProfile.dto.SwitchProfileResponse;
import com.example.authenticationBasedProfile.entity.User;

import com.example.authenticationBasedProfile.service.AuthService;

@RestController
@RequestMapping("/auth")
public class AuthController {

   @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest request) {
        AuthResponse response = authService.login(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/profile")
    public ResponseEntity<SwitchProfileResponse> selectProfile(
        @RequestHeader("Authorization") String token, 
        @RequestBody SwitchProfileRequest request
    ) {
        SwitchProfileResponse response = authService.selectProfile(token, request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody AuthRequest request) {
        User user = authService.registerUser(request.getEmail(), request.getPassword());
        return ResponseEntity.ok(user);
    }

}
