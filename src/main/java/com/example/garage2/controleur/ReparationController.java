package com.example.garage2.controleur;

import com.example.garage2.entite.*;
import com.example.garage2.repository.ReparationTypeRepository;
import com.example.garage2.repository.UtilisateurRepository;
import com.example.garage2.repository.VoitureRepository;
import com.example.garage2.service.EmailService;
import com.example.garage2.service.ReparationService;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


@CrossOrigin
@RestController
@RequestMapping("make")
public class ReparationController {
    private final ReparationService reparationService;
    private final VoitureRepository voitureRepository;
    private final ReparationTypeRepository reparationTypeRepository;
    private UtilisateurRepository utilisateurRepository;
    @Autowired
    private EmailService emailService;

    public ReparationController(ReparationService reparationService, VoitureRepository voituresRepository, ReparationTypeRepository reparationTypeRepository) {
        this.reparationService = reparationService;
        this.voitureRepository = voituresRepository;
        this.reparationTypeRepository = reparationTypeRepository;
        this.utilisateurRepository = utilisateurRepository;
    }

    @GetMapping
    public ResponseEntity<List<Reparation>> getAllReparations() {
        List<Reparation> reparation = reparationService.getAllReparations();
        return new ResponseEntity<>(reparation, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<String> makeReservation(@RequestBody Reparation reparation) throws Exception {
        // Extraire l'utilisateur actuellement authentifié
        Utilisateur utilisateur = (Utilisateur) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String userEmail = utilisateur.getEmail();
        // Récupérer l'ID de l'utilisateur
        int utilisateurId = utilisateur.getId();

        // Récupérer la plaque d'immatriculation de la réparation
        String plaqueImmatriculation = reparation.getPlaqueImmatriculation();

        // Trouver la voiture en fonction de la plaque d'immatriculation
        Voitures voiture = voitureRepository.findByPlaqueImmatriculation(plaqueImmatriculation);
        if (voiture == null) {
            return ResponseEntity.badRequest().body("Aucune voiture trouvée pour la plaque d'immatriculation fournie.");
        }

        // Associer la voiture à la réparation
        reparation.setVoiture(voiture);

        // Récupérer le type de réparation en fonction de son ID
        Long reparationTypeId = reparation.getReparationType().getId();
        ReparationType reparationType = reparationTypeRepository.findById(reparationTypeId)
                .orElseThrow(() -> new IllegalArgumentException("Type de réparation invalide: " + reparationTypeId));

        // Récupérer la durée de la réparation en fonction du type de réparation
        Long duree = reparationType.getDuree();
        reparation.setDuree(duree);
        LocalDateTime startDate= reparation.getStartDate();


        // Passer l'ID de l'utilisateur à votre service
        reparationService.makeReservation(reparation, utilisateurId);

        // Send confirmation email
        String subject = "Confirmation de création de reparation";
        emailService.sendNotificationEmail(userEmail, subject,startDate,duree,plaqueImmatriculation);

        return ResponseEntity.ok("Réservation effectuée avec succès.");
    }



    @DeleteMapping(path = "/{id}")
    public ResponseEntity<String> annulerReparation(@PathVariable Long id) {
        reparationService.deleteReparation(id);
        return ResponseEntity.ok("Suprimé avec succès.");
    }


    @PutMapping("/{id}")
    public ResponseEntity<Reparation> modifierInformationsReparation(@PathVariable Long id, @RequestBody Reparation reparationModifiee) {
        Utilisateur utilisateur = (Utilisateur) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Reparation reparationMissAJour = reparationService.updateReparation((long) utilisateur.getId(), id, reparationModifiee);

        if (reparationMissAJour != null) {
            return ResponseEntity.ok(reparationMissAJour);
        } else {
            return ResponseEntity.notFound().build();
        }
    }


}