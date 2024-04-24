package com.example.garage2.entite;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Voitures {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "marque_id")
    private Voiture_Marque marque;

    @ManyToOne
    @JoinColumn(name = "model_id")
    private Voiture_model model;


    @ManyToMany(mappedBy = "voitures")
    private List<Utilisateur> utilisateurs = new ArrayList<>();

    private Date anne_fabrication;
    private String couleur;
    private String boite;
    private String carroserie;
    private String carburant;
    private int kilometrage;
    @Column(name = "plaque_immatriculation")
    private String plaqueImmatriculation;

}
