package com.example.garage2.repository;

import com.example.garage2.entite.Voitures;
import org.springframework.data.jpa.repository.JpaRepository;

// Creating an interface called LicensePlateRecognitionRepository. It extends JpaRepository,
// allowing it to utilize JpaRepository's methods to interact with the data of the Voitures table in the database.
public interface LicensePlateRecognitionRepository extends JpaRepository<Voitures, Long> {
    // No additional methods are added here, as JpaRepository provides methods such as save, findAll, findById, delete, etc.
}
