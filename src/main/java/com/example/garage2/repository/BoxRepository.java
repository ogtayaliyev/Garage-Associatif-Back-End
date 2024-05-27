package com.example.garage2.repository;

// Importing the Box class, which represents an entity in our database,
// and the JpaRepository class from Spring Data JPA, which provides methods to interact with the database.
import com.example.garage2.entite.Box;
import org.springframework.data.jpa.repository.JpaRepository;

// Creating an interface called BoxRepository. It extends JpaRepository, which means it can use
// all the methods of JpaRepository to work with the data of the Box table in the database.
public interface BoxRepository extends JpaRepository<Box, Long> {
    // We don't need to add any additional methods here for now,
// because JpaRepository already provides many useful methods such as save, findAll, findById, delete, etc. findById, delete, etc.
}
