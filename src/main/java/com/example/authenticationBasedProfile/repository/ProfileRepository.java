package com.example.authenticationBasedProfile.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.authenticationBasedProfile.entity.Profile;

import java.util.List;

@Repository
public interface ProfileRepository extends JpaRepository<Profile, String> {
    List<Profile> findByUserId(String userId);
}
