package com.example.authenticationBasedProfile.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.authenticationBasedProfile.dto.AuthRequest;
import com.example.authenticationBasedProfile.dto.SwitchProfileRequest;
import com.example.authenticationBasedProfile.dto.AuthResponse;
import com.example.authenticationBasedProfile.dto.SwitchProfileResponse;
import com.example.authenticationBasedProfile.entity.Profile;
import com.example.authenticationBasedProfile.entity.Role;
import com.example.authenticationBasedProfile.entity.User;
import com.example.authenticationBasedProfile.infrastructure.security.JwtUtil;
import com.example.authenticationBasedProfile.repository.UserRepository;
import com.example.authenticationBasedProfile.repository.ProfileRepository;

import java.util.Collections;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProfileRepository profileRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    public User registerUser(String email, String password) {
        if (userRepository.findByEmail(email).isPresent()) {
            throw new RuntimeException("Email já cadastrado!");
        }
        
        User user = new User();
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password)); 
        Profile profile = new Profile();
        profile.setRole(Role.COMMON);
        profile.setName("USER NORMAL");
        profile.setUser(user);

        user.setProfiles(Collections.singletonList(profile));

        return userRepository.save(user);
    }

    public AuthResponse login(AuthRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        String token = jwtUtil.generateTokenLogin(user.getEmail());
        return new AuthResponse(token);
    }

    public SwitchProfileResponse selectProfile(String token, SwitchProfileRequest request) {
        if (!jwtUtil.validateToken(token.replace("Bearer ", ""))) {
            throw new RuntimeException("Invalid or expired token");
        }

        String email = jwtUtil.extractEmail(token.replace("Bearer ", ""));

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        Profile profile = profileRepository.findById(request.getProfileId())
                .orElseThrow(() -> new RuntimeException("Profile not found"));

        if (!user.getProfiles().contains(profile)) {
            throw new RuntimeException("Invalid profile selection");
        }

        String newToken = jwtUtil.generateToken(user.getEmail(), profile.getId(), profile.getRole().name());

        return new SwitchProfileResponse(newToken, profile.getRole().name());
    }
}