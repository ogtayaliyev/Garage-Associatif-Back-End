package com.example.garage2.repository;

import com.example.garage2.entite.ReparationType;
import org.springframework.data.jpa.repository.JpaRepository;

// Creating an interface called ReparationTypeRepository. It extends JpaRepository,
// allowing it to utilize JpaRepository's methods to interact with the data of the ReparationType table in the database.
public interface ReparationTypeRepository extends JpaRepository<ReparationType, Long> {
}
