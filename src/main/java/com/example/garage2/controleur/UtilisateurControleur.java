package com.example.garage2.controleur;

import com.example.garage2.entite.Utilisateur;
import com.example.garage2.entite.Voitures;
import com.example.garage2.repository.UtilisateurRepository;
import com.example.garage2.securite.JwtService;
import com.example.garage2.service.UtilisateurService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;


@Slf4j
@RestController
@CrossOrigin
@RequestMapping
public class UtilisateurControleur {

    private final AuthenticationManager authenticationManager;
    private final UtilisateurService utilisateurService;
    private final JwtService jwtService;
    @Autowired
    private UtilisateurRepository utilisateurRepository;

    public UtilisateurControleur(AuthenticationManager authenticationManager, UtilisateurService utilisateurService, JwtService jwtService) {
        this.authenticationManager = authenticationManager;
        this.utilisateurService = utilisateurService;
        this.jwtService = jwtService;
    }

    @GetMapping(path = "profil")
    public ResponseEntity<?> profil() {
        Utilisateur utilisateur = (Utilisateur) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (utilisateur != null) {
            return ResponseEntity.ok(utilisateur);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @PutMapping(path = "modifier")
    public ResponseEntity<Utilisateur> modifierInformationsUtilisateur(@RequestBody Utilisateur utilisateurModifie, @Autowired JwtService jwtService) {
        Utilisateur utilisateur = (Utilisateur) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Utilisateur utilisateurMisAJour = utilisateurService.mettreAJourInformations((long) utilisateur.getId(), utilisateurModifie);

        // Token renouvelé
        jwtService.replaceToken(utilisateurMisAJour);

        if (utilisateurMisAJour != null) {
            return ResponseEntity.ok(utilisateurMisAJour);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}

