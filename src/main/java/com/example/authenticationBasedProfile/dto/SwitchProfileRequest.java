package com.example.authenticationBasedProfile.dto;

public class SwitchProfileRequest {
    // private String email;
    // private String password;
    private String profileId; // O usuário deve escolher um perfil no login


    // Getters and Setters
    // public String getEmail() {
    //     return email;
    // }

    // public void setEmail(String email) {
    //     this.email = email;
    // }

    // public String getPassword() {
    //     return password;
    // }

    // public void setPassword(String password) {
    //     this.password = password;
    // }

    public String getProfileId() {
        return profileId;
    }

    public void setProfileId(String profileId) {
        this.profileId = profileId;
    }
}
