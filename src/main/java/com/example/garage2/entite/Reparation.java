package com.example.garage2.entite;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Reparation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String plaqueImmatriculation;
    private Long duree;

        @ManyToOne
    private ReparationType reparationType;

    private LocalDateTime startDate;
    private LocalDateTime endDate; // Yeni eklendi

    @ManyToOne
    @JoinColumn(name = "voiture_id")
    private Voitures voiture;

    @ManyToOne
    @JoinColumn(name = "utilisateur_id")
    private Utilisateur utilisateur;

}

