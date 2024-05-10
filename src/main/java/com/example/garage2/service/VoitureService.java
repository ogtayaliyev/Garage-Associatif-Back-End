package com.example.garage2.service;

import com.example.garage2.entite.Voiture_Marque;
import com.example.garage2.entite.Voiture_model;
import com.example.garage2.entite.Voitures;
import com.example.garage2.repository.MarqueRepository;
import com.example.garage2.repository.ModelRepository;
import com.example.garage2.repository.VoitureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VoitureService {

    @Autowired
    private VoitureRepository voitureRepository;
    @Autowired
    private ModelRepository modelRepository;
    @Autowired
    private MarqueRepository marqueRepository;

    public List<Voitures> getAllVoitures() {
        return voitureRepository.findAll();
    }

    public List<Voiture_Marque> getAllMarques() {
    return marqueRepository.findAll();
    }
    public Voitures addVoiture(Voitures voiture) {
        return voitureRepository.save(voiture);
    }

    public Voitures updateVoiture(Long id, Voitures voiture) {
        if (!voitureRepository.existsById(id)) {
            // Gérer l'exception si la voiture n'est pas trouvée
            return null;
        }
        voiture.setId(id);
        return voitureRepository.save(voiture);
    }
    public List<Voiture_model> getModelsByMarqueId(Long marqueId) {
        return modelRepository.findByMarqueId(marqueId);
    }


    public void deleteVoiture(Long id) {
        voitureRepository.deleteById(id);
    }

}
