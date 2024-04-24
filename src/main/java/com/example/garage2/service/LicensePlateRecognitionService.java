package com.example.garage2.service;

import com.example.garage2.entite.Voitures;
import com.example.garage2.repository.VoitureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LicensePlateRecognitionService {

    private final VoitureRepository voitureRepository;

    @Autowired
    public LicensePlateRecognitionService(VoitureRepository voitureRepository) {
        this.voitureRepository = voitureRepository;
    }

    public Voitures recognizeVehicleByLicensePlate(String plaqueImmatriculation) {
        return voitureRepository.findByPlaqueImmatriculation(plaqueImmatriculation);
    }
}
