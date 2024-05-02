package com.example.garage2.controleur;

import com.example.garage2.entite.Voiture_Marque;
import com.example.garage2.entite.Voiture_model;
import com.example.garage2.entite.Voitures;
import com.example.garage2.service.VoitureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("voitures")
public class VoitureController {

    @Autowired
    private VoitureService voitureService;

    @GetMapping
    public ResponseEntity<List<Voitures>> getAllVoitures() {
        List<Voitures> voitures = voitureService.getAllVoitures();
        return new ResponseEntity<>(voitures, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Voitures> addVoiture(@RequestBody Voitures voiture) {
        Voitures savedVoiture = voitureService.addVoiture(voiture);
        return new ResponseEntity<>(savedVoiture, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Voitures> updateVoiture(@PathVariable Long id, @RequestBody Voitures voiture) {
        Voitures updatedVoiture = voitureService.updateVoiture(id, voiture);
        if (updatedVoiture == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(updatedVoiture, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVoiture(@PathVariable Long id) {
        voitureService.deleteVoiture(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("models")
    public ResponseEntity<List<Voiture_model>> getAllModels() {
        List<Voiture_model> voitures = voitureService.getAllModels();
        return new ResponseEntity<>(voitures, HttpStatus.OK);
    }

    @GetMapping("marques")
    public ResponseEntity<List<Voiture_Marque>> getAllMarques() {
        List<Voiture_Marque> voitures = voitureService.getAllMarques();
        return new ResponseEntity<>(voitures, HttpStatus.OK);
    }
}

