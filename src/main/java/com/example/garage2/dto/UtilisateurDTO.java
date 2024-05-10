package com.example.garage2.dto;


import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
@Getter
@Setter
public class UtilisateurDTO {
    private int id;
    private String nom;
    private String prenom;
    private String email;
    private String phone_number;
    private String adresse;
    private Instant dateCreation;
    private Instant dateModification;
    private boolean actif;
    private RoleDTO role;

}
