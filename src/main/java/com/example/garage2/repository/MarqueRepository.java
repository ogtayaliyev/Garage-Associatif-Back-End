package com.example.garage2.repository;

import com.example.garage2.entite.Voiture_Marque;
import org.springframework.data.jpa.repository.JpaRepository;

// Creating an interface called MarqueRepository. It extends JpaRepository,
// allowing it to utilize JpaRepository's methods to interact with the data of the Voiture_Marque table in the database.
public interface MarqueRepository extends JpaRepository<Voiture_Marque, Long> {
}