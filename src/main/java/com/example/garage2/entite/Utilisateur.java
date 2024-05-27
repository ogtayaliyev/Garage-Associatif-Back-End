package com.example.garage2.entite;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.Instant;
import java.util.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "utilisateur")
public class Utilisateur implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String nom;
    private String prenom;
    private String password;
    private String email;
    private String phone_number;
    private String adresse;
    @CreationTimestamp
    private Instant dateCreation;
    @UpdateTimestamp
    private Instant dateModification;
    private boolean actif = false;

    @OneToOne(cascade = CascadeType.ALL)
    private Role role;

    public String getEmail() {
        return this.email;
    }

    @OneToMany(mappedBy="utilisateur",fetch = FetchType.EAGER)
    private List<Voitures> voitures;

    @OneToMany(mappedBy="utilisateur",fetch = FetchType.EAGER)
    private List<LocationBox> locationBoxes;

    @OneToMany(mappedBy="utilisateur",fetch = FetchType.EAGER)
    private List<Reparation> reparations;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority("ROLE_"+this.role.getLibelle()));
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.nom;
    }

    @Override
    public boolean isAccountNonExpired() {
        return this.actif;
    }

    @Override
    public boolean isAccountNonLocked() {
        return this.actif;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return this.actif;
    }

    @Override
    public boolean isEnabled() {
        return this.actif;
    }


}
