package com.example.garage2.service;

import com.example.garage2.entite.Utilisateur;
import com.example.garage2.entite.Voiture_Marque;
import com.example.garage2.entite.Voiture_model;
import com.example.garage2.entite.Voitures;
import com.example.garage2.repository.MarqueRepository;
import com.example.garage2.repository.ModelRepository;
import com.example.garage2.repository.UtilisateurRepository;
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
    @Autowired
    private UtilisateurRepository utilisateurRepository;

    public List<Voitures> getAllVoitures() {
        return voitureRepository.findAll();
    }

    public List<Voiture_Marque> getAllMarques() {
    return marqueRepository.findAll();
    }

    public void addVoiture(int idUtilisateur, Voitures voitures) {

        Utilisateur utilisateur = utilisateurRepository.findById(idUtilisateur)
                .orElseThrow(() -> new RuntimeException("Utilisateur introuvable avec l'ID : " + idUtilisateur));

        // Associer la voiture à l'utilisateur
        voitures.setUtilisateur(utilisateur);

        // Sauvegarder la voiture dans la base de données
        voitureRepository.save(voitures);
    }

    public Voitures updateVoiture(Long userId, Long voitureId, Voitures voitureModifiee) {
        // List des Voitures utilisateur connecté
        List<Voitures> kullaniciAraclari = voitureRepository.findByUtilisateurId(userId);

        // Trouver voiture utilisateure dans les voitures existent
        Voitures voitureExistante = null;
        for (Voitures voiture : kullaniciAraclari) {
            if (voiture.getId().equals(voitureId)) {
                voitureExistante = voiture;
                break;
            }
        }

        if (voitureExistante == null) {
            return null; //
        }

        if (voitureModifiee.getKilometrage() != null) {
            voitureExistante.setKilometrage(voitureModifiee.getKilometrage());
        }
        voitureExistante.setEtatVente(voitureModifiee.isEtatVente());
        return voitureRepository.save(voitureExistante);
    }



    public List<Voiture_model> getModelsByMarqueId(Long marqueId) {
        return modelRepository.findByMarqueId(marqueId);
    }


    public void deleteVoiture(Long id) {
        voitureRepository.deleteById(id);
    }

}
