package com.example.garage2.entite;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Voiture_Marque {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    private String marque_nom;
    private String pays_fabriquant;





//
//    @OneToMany(mappedBy="marque", cascade = CascadeType.ALL,fetch = FetchType.EAGER)
//    private List<Voiture_model> models = new ArrayList<>();

}
