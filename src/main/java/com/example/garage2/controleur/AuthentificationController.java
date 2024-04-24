package com.example.garage2.controleur;

import com.example.garage2.dto.AuthentificationDTO;
import com.example.garage2.entite.Utilisateur;
import com.example.garage2.securite.JwtService;
import com.example.garage2.service.UtilisateurService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Slf4j
@RestController
@CrossOrigin
@RequestMapping
public class AuthentificationController {

    private final AuthenticationManager authenticationManager;
    private final UtilisateurService utilisateurService;
    private final JwtService jwtService;

    public AuthentificationController(AuthenticationManager authenticationManager, UtilisateurService utilisateurService, JwtService jwtService) {
        this.authenticationManager = authenticationManager;
        this.utilisateurService = utilisateurService;
        this.jwtService = jwtService;
    }

    @PostMapping(path = "inscription",consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> inscription(@RequestBody Utilisateur utilisateur) {
        log.info("Inscription");
        utilisateurService.inscription(utilisateur);
        return ResponseEntity.ok().build();
    }

    @PostMapping(path = "activation")
    public void activation(@RequestBody Map<String, String> activation) {
        this.utilisateurService.activation(activation);
    }


    @PostMapping(path = "connexion",consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity connexion(@RequestBody AuthentificationDTO authentificationDTO) {
        final Authentication authenticate = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authentificationDTO.getEmail(), authentificationDTO.getPassword())
        );
        if(authenticate.isAuthenticated()) {
            Map<String,String> token = jwtService.generate(authentificationDTO.getEmail());
            return ResponseEntity.ok(token);
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }


}
