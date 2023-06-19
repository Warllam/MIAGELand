package com.miageland.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
@Getter
@Setter
@Table(name = "Billet")
public class Billet {

    private  @Id @GeneratedValue(strategy = GenerationType.IDENTITY) Long id;
    private Date dateValidite;
    private float prix;
    private EtatBillet etatBillet;
    private Date dateVente;
    private Date dateVisite;
    private boolean estPaye;
    @ManyToOne
    @JoinColumn(name = "visiteur_id")
    @JsonBackReference
    private Visiteur visiteur;

    public Billet(Date pDateValidite, float pPrix, Date dDateVente, Visiteur visiteur){
        this.dateValidite = pDateValidite;
        this.prix = pPrix;
        this.dateVente = dDateVente;
        this.etatBillet = EtatBillet.VALIDE;
        this.visiteur = visiteur;
    }

    public Billet(){}
}
