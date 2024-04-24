package com.example.garage2.service;
import com.example.garage2.entite.*;
import com.example.garage2.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;

import java.time.temporal.ChronoUnit;
import java.util.Optional;

@Service
public class BoxService {
    @Autowired
    private BoxRepository boxRepository;

    @Autowired
    private UtilisateurRepository utilisateurRepository;
    @Autowired
    private LocationBoxRepository locationBoxRepository;
    @Autowired
    private EmailService emailService;

    public float calculerPrix(LocalDate startDate, LocalDate returnDate, float prix_loc) {
        // Calculer la durée totale de la location en heures
        long hoursBetween = ChronoUnit.HOURS.between(startDate.atStartOfDay(), returnDate.atStartOfDay());

        // Si la durée est égale à zéro ou négative, considérer la location comme d'une heure
        if (hoursBetween <= 0) {
            hoursBetween = 1;
        }

        // Calculer le prix total en fonction du nombre d'heures de location et du prix par heure
        float prixTotal = hoursBetween * prix_loc;

        return prixTotal;
    }


    public Box findBoxById(Integer id) {
        return boxRepository.findById(Long.valueOf(id)).orElseThrow(() -> new RuntimeException("Box introuvable"));
    }

    public void saveLocation(LocationBox locationBox, int idUtilisateur) throws Exception {
        try {
            // Charger l'utilisateur à partir de la base de données en utilisant son ID
            Optional<Utilisateur> utilisateurOptional = utilisateurRepository.findById(idUtilisateur);
            Utilisateur utilisateur = utilisateurOptional.orElseThrow(() -> new Exception("Utilisateur introuvable pour l'ID fourni."));

            // Extraire la Box de la LocationBox
            Box box = findBoxById(Math.toIntExact(locationBox.getBox().getId()));

            // Vérifier si la Box est valide
            if (box == null) {
                throw new IllegalArgumentException("La Box associée à la LocationBox est nulle.");
            }

            // Convertir les dates au format LocalDateTime si nécessaire
            LocalDateTime startDate = locationBox.getStartDate();
            LocalDateTime returnDate = locationBox.getReturnDate();

            // Récupérer la date et l'heure séparément
            LocalDate startDateOnly = startDate.toLocalDate();
            LocalDate returnDateOnly = returnDate.toLocalDate();


// Vérifier si des locations conflictuelles existent dans la base de données pour cette période
            boolean conflictingLocationsExist = locationBoxRepository.existsConflictingLocations(box, startDate, returnDate, idUtilisateur);
            if (conflictingLocationsExist) {
                throw new RuntimeException("La boîte est déjà réservée pour cette période");
            }

// Calculer la durée de la réservation en heures
            long hoursBetween = ChronoUnit.HOURS.between(startDate, returnDate);
            if (hoursBetween > 3) {
                throw new IllegalArgumentException("La durée de la réservation ne peut pas dépasser trois heures.");
            }

            // Vérifier si des réservations conflictuelles existent dans la base de données pour cette période
            boolean conflictingReservationsExist = locationBoxRepository.existsConflictingLocations(box, startDate, returnDate, idUtilisateur);
            if (conflictingReservationsExist) {
                throw new RuntimeException("La boîte est déjà réservée pour cette période");
            }


            // Calculer le prix de la location
            float prixTotal = (float)calculerPrix(startDateOnly, returnDateOnly, box.getPrix_loc());

            // Attribution de l'utilisateur à la location
            locationBox.setUtilisateur(utilisateur);

            // Définir le prix total calculé dans la LocationBox
            locationBox.setPrix_loc(prixTotal);

            // Enregistrer la LocationBox dans la base de données
            locationBoxRepository.save(locationBox);
        } catch (Exception e) {
            throw new Exception("Erreur lors de la création de la location : " + e.getMessage());
        }
    }


}
