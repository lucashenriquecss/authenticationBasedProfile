package com.example.authenticationBasedProfile.dto;

public class AuthRequest {
    private String email;
    private String password;
    // private String profileName;
    private String profileId; // O usuário deve escolher um perfil no login


    // Getters and Setters
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    // public String getProfileName() {
    //     return profileName;
    // }

    // public void setProfileName(String profileName) {
    //     this.profileName = profileName;
    // }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getProfileId() {
        return profileId;
    }

    public void setProfileId(String profileId) {
        this.profileId = profileId;
    }
}
