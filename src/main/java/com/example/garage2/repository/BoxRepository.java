package com.example.garage2.repository;

import com.example.garage2.entite.Box;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface BoxRepository extends JpaRepository<Box, Long> {

}
