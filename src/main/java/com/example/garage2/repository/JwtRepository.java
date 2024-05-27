package com.example.garage2.repository;

// On importe la classe Jwt, qui représente une entité dans notre base de données,
// et la classe CrudRepository de Spring Data, qui fournit des méthodes de base pour interagir avec la base de données.
import com.example.garage2.entite.Jwt;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

// On crée une interface qui s'appelle JwtRepository. Elle hérite de CrudRepository, ce qui signifie
// qu'elle peut utiliser toutes les méthodes de CrudRepository pour travailler avec les données de la table Jwt dans la base de données.
public interface JwtRepository extends CrudRepository<Jwt, Integer> {

    // Cette méthode cherche un JWT (un token de sécurité) dans la base de données en utilisant trois critères :
    // la valeur du token (valeur), s'il est désactivé (desactive) et s'il est expiré (expire).
    // Si elle trouve un token qui correspond à ces critères, elle retourne un Optional contenant ce token.
    // Si aucun token ne correspond, elle retourne un Optional vide.
    Optional<Jwt> findByValeurAndDesactiveAndExpire(String valeur, boolean desactive, boolean expire);
}
