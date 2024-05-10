package com.example.garage2.controleur;

import com.example.garage2.entite.*;
import com.example.garage2.service.BoxService;
import com.example.garage2.service.EmailService;
import com.example.garage2.service.LocationBoxNotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
public class LocationController {

    @Autowired
    private LocationBoxNotificationService locationBoxNotificationService;
    @Autowired
    private BoxService boxService;
    @Autowired
    private EmailService emailService;

    @PostMapping("locationbox")
    public ResponseEntity<String> creerLocationbox(@RequestBody LocationBox locationBox) {
        try {
            // Extraire l'utilisateur actuellement authentifié
            Utilisateur utilisateur = (Utilisateur) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            String userEmail = utilisateur.getEmail();
            // Vérifier si un utilisateur est authentifié
            if (utilisateur != null) {
                if (locationBox.getBoxId()==null) {
                    return ResponseEntity.badRequest().body("L'ID de la boîte est requis.");}
                boxService.saveLocation(locationBox,utilisateur.getId());

                // Send confirmation email
                String subject = "Confirmation de création de location";
                String message = "Votre location de boîte a été créée avec succès.";
                emailService.sendConfirmationEmail(userEmail, subject, message);

                // Envoyer la notification 24 heures avant le début de la location
                locationBoxNotificationService.sendNotification();

                return ResponseEntity.ok("Location créée avec succès.");
            } else {
                // Aucun utilisateur n'est authentifié
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Utilisateur non authentifié");
            }
        } catch (Exception e) {
            // Gérer les exceptions
            return ResponseEntity.badRequest().body("Erreur lors de la création de la location : " + e.getMessage());
        }
    }


}
