package com.miageland.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "Visiteur")
public class Visiteur {
    private  @Id @GeneratedValue(strategy = GenerationType.IDENTITY) @Column(name = "visiteur_id") Long id;
    private String nom;
    private String prenom;
    private String mail;
    @OneToMany(mappedBy = "visiteur", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Billet> billetsVisiteur;

    public Visiteur(String pNom, String pPrenom, String pMail){
        this.nom = pNom;
        this.prenom = pPrenom;
        this.mail = pMail;
    }

    public Visiteur() {
    }

    public int getNbVisites() {
        int nbVisites = 0;
        for (Billet billet : billetsVisiteur) {
            if (billet.getEtatBillet() == EtatBillet.UTILISE) {
                nbVisites ++;
            }
        }
        return nbVisites;
    }
}
