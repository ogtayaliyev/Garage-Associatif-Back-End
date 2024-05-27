package com.example.garage2.entite;

import com.fasterxml.jackson.annotation.JsonBackReference;
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
public class Voiture_model {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    private String model_nom;
    private Date mise_circulation;
    private String moteure_type;
    private int moteure_puissance;


    @ManyToOne
    @JoinColumn(name = "marque_id", nullable = false)
    private Voiture_Marque marque;
}
