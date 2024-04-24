package com.example.garage2.repository;


import com.example.garage2.entite.Utilisateur;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UtilisateurRepository extends CrudRepository<Utilisateur, Integer> {


    Optional<Utilisateur> findByEmail(String email);


    Optional<Utilisateur> findById(Long id);

    void deleteById(Long id);
}
