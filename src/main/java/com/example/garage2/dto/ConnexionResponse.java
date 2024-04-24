package com.example.garage2.dto;


import com.example.garage2.entite.Utilisateur;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ConnexionResponse {
    private String token;
    private Utilisateur utilisateur;

    public ConnexionResponse(String token, Utilisateur utilisateur) {
        this.token = token;
        this.utilisateur = utilisateur;
    }

    public ConnexionResponse() {

    }
}
