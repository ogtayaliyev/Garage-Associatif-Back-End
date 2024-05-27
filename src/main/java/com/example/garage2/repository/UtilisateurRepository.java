package com.example.garage2.repository;


import com.example.garage2.entite.Utilisateur;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

// Creating an interface called UtilisateurRepository. It extends CrudRepository,
// allowing it to utilize CrudRepository's methods to interact with the data of the Utilisateur table in the database.
public interface UtilisateurRepository extends CrudRepository<Utilisateur, Integer> {

    // Method to find a user by email.
    Optional<Utilisateur> findByEmail(String email);

    // Method to find a user by ID.
    Optional<Utilisateur> findById(Long id);

    // Method to delete a user by ID.
    void deleteById(Long id);
    boolean existsByEmail(String email);
}
