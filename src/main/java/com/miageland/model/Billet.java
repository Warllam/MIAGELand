package com.miageland.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Date;

@Entity
@Getter
@Setter
@Table(name = "Billet")
public class Billet {

    private  @Id @GeneratedValue(strategy = GenerationType.IDENTITY) Long id;
    private LocalDate dateValidite;
    private float prix;
    private EtatBillet etatBillet;
    private Date dateVente;
    private boolean estPaye;
    @ManyToOne
    @JoinColumn(name = "visiteur_id")
    @JsonBackReference
    private Visiteur visiteur;

    public Billet(LocalDate pDateValidite, float pPrix, Date dDateVente, Visiteur visiteur){
        this.dateValidite = pDateValidite;
        this.prix = pPrix;
        this.dateVente = dDateVente;
        this.etatBillet = EtatBillet.ATTENTE;
        this.visiteur = visiteur;
    }

    public Billet(){}
}
