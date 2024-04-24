package com.example.garage2.service;



import com.example.garage2.TypeDeRole;
import com.example.garage2.entite.Role;
import com.example.garage2.entite.Utilisateur;
import com.example.garage2.entite.Validation;
import com.example.garage2.entite.Voitures;
import com.example.garage2.repository.UtilisateurRepository;
import com.example.garage2.repository.VoitureRepository;
import com.example.garage2.securite.PasswordValidator;
import lombok.AllArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class UtilisateurService implements UserDetailsService {
    private UtilisateurRepository utilisateurRepository;
    private BCryptPasswordEncoder passwordEncoder;
    private ValidationService validationService;

    @Autowired
    private VoitureRepository voitureRepository;

    public void inscription(Utilisateur utilisateur) {

        PasswordValidator passwordValidator = new PasswordValidator();
        if (!passwordValidator.isPasswordValid(utilisateur.getPassword())) {
            throw new RuntimeException("Le mot de passe ne répond pas aux critères de complexité.");
        }

        if(!utilisateur.getEmail().contains("@")) {
            throw  new RuntimeException("Votre mail invalide");
        }
        if(!utilisateur.getEmail().contains(".")) {
            throw  new RuntimeException("Votre mail invalide");
        }
//        utilisateur.setActif(Boolean.TRUE);
        Optional<Utilisateur> utilisateurOptional = this.utilisateurRepository.findByEmail(utilisateur.getEmail());
        if(utilisateurOptional.isPresent()) {
            throw  new RuntimeException("Votre mail est déjà utilisé");
        }
        String mdpCrypte = this.passwordEncoder.encode(utilisateur.getPassword());
        utilisateur.setPassword(mdpCrypte);

        Role roleUtilisateur = new Role();
        roleUtilisateur.setLibelle(TypeDeRole.UTILISATEUR);
        utilisateur.setRole(roleUtilisateur);

        utilisateur = this.utilisateurRepository.save(utilisateur);
        this.validationService.enregistrer(utilisateur);

    }

    public void activation(Map<String, String> activation) {
        Validation validation = this.validationService.lireEnFonctionDuCode(activation.get("code"));
        if(Instant.now().isAfter(validation.getExpiration())){
            throw  new RuntimeException("Votre code a expiré");
        }
        Utilisateur utilisateurActiver = this.utilisateurRepository.findById(validation.getUtilisateur().getId()).orElseThrow(() -> new RuntimeException("Utilisateur inconnu"));
        utilisateurActiver.setActif(true);
        this.utilisateurRepository.save(utilisateurActiver);
    }



    @Override
    public Utilisateur loadUserByUsername(String email) throws UsernameNotFoundException {
        return this.utilisateurRepository
                .findByEmail(email)
                .orElseThrow(() -> new  UsernameNotFoundException("Aucun utilisateur ne corespond à cet identifiant"));
    }


    public Utilisateur mettreAJourInformations(Long id, Utilisateur utilisateurModifie) {
        Utilisateur utilisateurExistant = (Utilisateur) utilisateurRepository.findById(id).orElse(null);

        if (utilisateurModifie.getNom() != null) {
            utilisateurExistant.setNom(utilisateurModifie.getNom());
        }
        if (utilisateurModifie.getPrenom() != null) {
            utilisateurExistant.setPrenom(utilisateurModifie.getPrenom());
        }
        if (utilisateurModifie.getEmail() != null) {
            utilisateurExistant.setEmail(utilisateurModifie.getEmail());
        }
        if (utilisateurModifie.getPhone_number() != null) {
            utilisateurExistant.setPhone_number(utilisateurModifie.getPhone_number());
        }

        // Ajout de nouvelles voitures
        if (utilisateurModifie.getVoitures() != null && !utilisateurModifie.getVoitures().isEmpty()) {
            List<Voitures> nouvellesVoitures = utilisateurModifie.getVoitures().stream()
                    .map(voiture -> {
                        // Vérifiez si la voiture existe déjà dans la base de données
                        Voitures voitureExistante = voitureRepository.findByPlaqueImmatriculation(voiture.getPlaqueImmatriculation());
                        if (voitureExistante == null) {
                            // Si la voiture n'existe pas, enregistrez-la dans la base de données
                            return voitureRepository.save(voiture);
                        } else {
                            // Si la voiture existe déjà, retournez-la
                            return voitureExistante;
                        }
                    })
                    .collect(Collectors.toList());

            // Associez les nouvelles voitures à l'utilisateur
            utilisateurExistant.getVoitures().addAll(nouvellesVoitures);
        }
        return utilisateurRepository.save(utilisateurExistant);
    }

}

