package com.example.garage2.repository;


import com.example.garage2.entite.Voitures;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

// Creating an interface called VoitureRepository. It extends JpaRepository,
// allowing it to utilize JpaRepository's methods to interact with the data of the Voitures table in the database.
public interface VoitureRepository extends JpaRepository<Voitures, Long> {

    // Method to find a car by license plate.
    Voitures findByPlaqueImmatriculation(String plaqueImmatriculation);

    // Method to find cars by user ID.
    List<Voitures> findByUtilisateurId(Long userId);
}
