package com.example.garage2.repository;


import com.example.garage2.entite.Box;
import com.example.garage2.entite.LocationBox;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

// Creating an interface called LocationBoxRepository. It extends JpaRepository,
// allowing it to utilize JpaRepository's methods to interact with the data of the LocationBox table in the database.
public interface LocationBoxRepository extends JpaRepository<LocationBox, Long> {
    List<LocationBox> findByReturnDateBeforeAndEtatLocationNot(LocalDateTime returnDate, String etatLocation);

    // Method to find locations by user ID.
    List<LocationBox> findByUtilisateurId(Long userId);

    // Custom query to find locations for notification.
    @Query("SELECT lb FROM LocationBox lb WHERE lb.startDate <= :notificationDateTime AND lb.startDate > :currentDateTime")
    List<LocationBox> findLocationsForNotification(@Param("notificationDateTime") LocalDateTime notificationDateTime, @Param("currentDateTime") LocalDateTime currentDateTime);

    // Custom query to check for conflicting locations.
    @Query("SELECT CASE WHEN COUNT(l) > 0 THEN true ELSE false END FROM LocationBox l WHERE l.box = :box " +
            "AND ((l.startDate <= :endDate AND l.returnDate >= :startDate) OR (l.startDate >= :startDate AND l.startDate <= :endDate)) " +
            "AND l.utilisateur.id != :userId")
    boolean existsConflictingLocations(@Param("box") Box box,
                                       @Param("startDate") LocalDateTime startDate,
                                       @Param("endDate") LocalDateTime endDate,
                                       @Param("userId") int userId);

    List<LocationBox> findByReturnDateAfterAndEtatLocationNot(LocalDateTime now, String etatLocation);
}
