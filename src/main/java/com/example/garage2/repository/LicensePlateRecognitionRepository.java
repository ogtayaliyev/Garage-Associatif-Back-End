package com.example.garage2.repository;

import com.example.garage2.entite.Voitures;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LicensePlateRecognitionRepository extends JpaRepository<Voitures, Long> {
}
