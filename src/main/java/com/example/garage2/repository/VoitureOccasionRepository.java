package com.example.garage2.repository;

import com.example.garage2.entite.Voiture_ocasion;
import org.springframework.data.jpa.repository.JpaRepository;

// Creating an interface called VoitureOccasionRepository. It extends JpaRepository,
// allowing it to utilize JpaRepository's methods to interact with the data of the Voiture_ocasion table in the database.
public interface VoitureOccasionRepository extends JpaRepository<Voiture_ocasion, Long> {
}
