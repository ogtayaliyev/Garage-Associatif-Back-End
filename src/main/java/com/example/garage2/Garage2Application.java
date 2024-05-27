package com.example.garage2;

import com.example.garage2.entite.Role;
import com.example.garage2.entite.Utilisateur;
import com.example.garage2.repository.UtilisateurRepository;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.crypto.password.PasswordEncoder;


@AllArgsConstructor
@EnableScheduling
@SpringBootApplication
public class Garage2Application implements CommandLineRunner {

    UtilisateurRepository utilisateurRepository;
    PasswordEncoder passwordEncoder;

    public static void main(String[] args) {
        SpringApplication.run(Garage2Application.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        createUtilisateurIfNotExists("admin", "ogtayaliyev9@gmail.com", "Admin2024.", TypeDeRole.ADMINISTRATEUR);
    }

    private void createUtilisateurIfNotExists(String nom, String email, String password, TypeDeRole roleType) {
        if (utilisateurRepository.findByEmail(email).isEmpty()) {
            Utilisateur utilisateur = Utilisateur.builder()
                    .actif(true)
                    .nom(nom)
                    .email(email)
                    .password(passwordEncoder.encode(password))
                    .role(
                            Role.builder()
                                    .libelle(roleType)
                                    .build()
                    )
                    .build();
            utilisateurRepository.save(utilisateur);
        }
    }
}
