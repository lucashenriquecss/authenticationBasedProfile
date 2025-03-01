package com.example.authenticationBasedProfile.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.authenticationBasedProfile.repository.UserRepository;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

   
}
