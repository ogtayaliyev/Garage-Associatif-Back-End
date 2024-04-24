package com.example.garage2.repository;


import com.example.garage2.entite.Voitures;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VoitureRepository extends JpaRepository<Voitures,Long> {
    Voitures findByPlaqueImmatriculation(String plaqueImmatriculation);
}
