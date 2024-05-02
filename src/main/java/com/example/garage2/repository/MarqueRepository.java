package com.example.garage2.repository;

import com.example.garage2.entite.Voiture_Marque;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MarqueRepository extends JpaRepository<Voiture_Marque,Long> {
}
