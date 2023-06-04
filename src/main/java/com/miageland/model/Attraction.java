package com.miageland.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "Attraction")
public class Attraction {

    private @Id @GeneratedValue(strategy = GenerationType.IDENTITY) Long id;
    private String nom;
    private boolean estOuverte;

    public Attraction(String pNom, boolean pEstOuverte){
        this.nom = pNom;
        this.estOuverte = pEstOuverte;
    }

    public Attraction() {
    }

    public void setEstOuverte(Boolean estOuverte) {
        this.estOuverte = estOuverte;
    }
}
