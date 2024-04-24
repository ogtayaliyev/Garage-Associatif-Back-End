package com.example.garage2.controleur;

import com.example.garage2.entite.Voiture_ocasion;
import com.example.garage2.repository.VoitureOccasionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping
public class VoitureOccasionController {

 @Autowired
  private VoitureOccasionRepository voitureOccasionRepository;

    @GetMapping("voitureOccasion")
    public List<Voiture_ocasion> getAllVoitures() {

        return voitureOccasionRepository.findAll();
    }
}
