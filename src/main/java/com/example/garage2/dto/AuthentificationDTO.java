package com.example.garage2.dto;

public record AuthentificationDTO(String email, String password) {
    public String getEmail() {
        return email;
    }

    public Object getPassword() {
        return  password;
    }
}
