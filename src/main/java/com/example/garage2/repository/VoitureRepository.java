package com.example.garage2.repository;


import com.example.garage2.entite.Voitures;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VoitureRepository extends JpaRepository<Voitures,Long> {
    Voitures findByPlaqueImmatriculation(String plaqueImmatriculation);
    List<Voitures> findByUtilisateurId(Long userId);
}
