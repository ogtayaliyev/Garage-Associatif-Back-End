package com.example.garage2.service;

import com.example.garage2.entite.Voiture_ocasion;
import com.example.garage2.repository.VoitureOccasionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class VoitureOcasionService {

    private final VoitureOccasionRepository voitureOccasionRepository;

    @Autowired
    public VoitureOcasionService(VoitureOccasionRepository voitureOccasionRepository) {
        this.voitureOccasionRepository = voitureOccasionRepository;
    }

    public List<Voiture_ocasion> getAllVoitures() {
        return voitureOccasionRepository.findAll();
    }
}
