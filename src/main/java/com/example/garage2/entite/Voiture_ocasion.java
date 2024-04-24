package com.example.garage2.entite;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Voiture_ocasion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    private float prix;
    private int garantie;
    private Date date_achat;
    private Date date_vente;


    @ManyToOne
    @JoinColumn(name = "voiture_id")
    private Voitures voitures;

    @ManyToOne
    @JoinColumn(name = "marque_id")
    private Voiture_Marque voiture_marque;

    @ManyToOne
    @JoinColumn(name = "model_id")
    private Voiture_model voiture_model;

}
