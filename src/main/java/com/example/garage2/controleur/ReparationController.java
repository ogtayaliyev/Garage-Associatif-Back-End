package com.example.garage2.controleur;

import com.example.garage2.entite.Reparation;
import com.example.garage2.entite.ReparationType;
import com.example.garage2.entite.Utilisateur;
import com.example.garage2.entite.Voitures;
import com.example.garage2.repository.ReparationTypeRepository;
import com.example.garage2.repository.UtilisateurRepository;
import com.example.garage2.repository.VoitureRepository;
import com.example.garage2.service.ReparationService;


import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;


@CrossOrigin
@RestController
@RequestMapping
public class ReparationController {
    private final ReparationService reparationService;
    private final VoitureRepository voitureRepository;
    private final ReparationTypeRepository reparationTypeRepository;
    private UtilisateurRepository utilisateurRepository;

    public ReparationController(ReparationService reparationService, VoitureRepository voituresRepository, ReparationTypeRepository reparationTypeRepository) {
        this.reparationService = reparationService;
        this.voitureRepository = voituresRepository;
        this.reparationTypeRepository = reparationTypeRepository;
        this.utilisateurRepository = utilisateurRepository;
    }
    @PostMapping("/make")
    public ResponseEntity<String> makeReservation(@RequestBody Reparation reparation) throws Exception {
        // Extraire l'utilisateur actuellement authentifié
        Utilisateur utilisateur = (Utilisateur) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

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

        // Passer l'ID de l'utilisateur à votre service
        reparationService.makeReservation(reparation, utilisateurId);

        return ResponseEntity.ok("Réservation effectuée avec succès.");
    }



}