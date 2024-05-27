package com.example.garage2.entite;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "location_box")
public class LocationBox {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime startDate;
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime returnDate;
    private float prix_loc;
    private String etatLocation;


    public float getPrixLoc() {
        return prix_loc;
    }
    @ManyToOne
    @JoinColumn(name = "box_id", nullable = false)
    private Box box;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "utilisateur_id")
    private Utilisateur utilisateur;


    public Long getBoxId() {
        if (box != null) {
            return box.getId();
        }
        return null;
    }
}

