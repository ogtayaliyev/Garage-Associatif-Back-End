package com.example.garage2.repository;

import com.example.garage2.entite.Reparation;
import com.example.garage2.entite.ReparationType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.time.LocalDateTime;


import java.util.List;


public interface ReparationRepository extends JpaRepository<Reparation,Long> {
    @Query("SELECT r FROM Reparation r WHERE r.reparationType = :reparationType AND r.endDate > CURRENT_TIMESTAMP")
    List<Reparation> findActiveReservationsByReparationType(@Param("reparationType") ReparationType reparationType);

    @Query("SELECT rt FROM ReparationType rt WHERE rt.id = :reparationTypeId")
    ReparationType findReparationTypeById(@Param("reparationTypeId") Long reparationTypeId);

    List<Reparation> findByUtilisateurId(Long userId);
    List<Reparation> findByEndDateBeforeAndEtatReparationNot(LocalDateTime endDate, String etatReparation);
    List<Reparation> findByEndDateAfterAndEtatReparationNot(LocalDateTime endDate, String etatReparation);
}
