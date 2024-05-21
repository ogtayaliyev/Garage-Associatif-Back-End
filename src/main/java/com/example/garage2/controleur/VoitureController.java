package com.example.garage2.controleur;

import com.example.garage2.entite.Utilisateur;
import com.example.garage2.entite.Voiture_Marque;
import com.example.garage2.entite.Voiture_model;
import com.example.garage2.entite.Voitures;
import com.example.garage2.repository.VoitureRepository;
import com.example.garage2.service.UtilisateurService;
import com.example.garage2.service.VoitureService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("voitures")
public class VoitureController {

    @Autowired
    private VoitureService voitureService;
    @Autowired
    private UtilisateurService utilisateurService;
    VoitureRepository voitureRepository;

    @GetMapping
    public ResponseEntity<List<Voitures>> getAllVoitures() {
        List<Voitures> voitures = voitureService.getAllVoitures();
        return new ResponseEntity<>(voitures, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<String> ajouterVoiture(@RequestBody Voitures voitures) {
        try {
            Utilisateur utilisateur = (Utilisateur) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            voitureService.addVoiture(utilisateur.getId(), voitures);
            return ResponseEntity.ok("Voiture ajoutée avec succès.");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }}

    @PutMapping("/{id}")
    public ResponseEntity<Voitures> modifierInformationsVoiture(@PathVariable Long id, @RequestBody Voitures voitureModifiee) {
        Utilisateur utilisateur = (Utilisateur) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Voitures voitureMiseAJour = voitureService.updateVoiture((long) utilisateur.getId(), id, voitureModifiee);

        if (voitureMiseAJour != null) {
            return ResponseEntity.ok(voitureMiseAJour);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVoiture(@PathVariable Long id) {
        voitureService.deleteVoiture(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("models")
    public ResponseEntity<List<Voiture_model>> getModelsByMarqueId(@RequestParam Long marqueId) {
        List<Voiture_model> models = voitureService.getModelsByMarqueId(marqueId);
        return new ResponseEntity<>(models, HttpStatus.OK);
    }


    @GetMapping("marques")
    public ResponseEntity<List<Voiture_Marque>> getAllMarques() {
        List<Voiture_Marque> voitures = voitureService.getAllMarques();
        return new ResponseEntity<>(voitures, HttpStatus.OK);
    }
}

