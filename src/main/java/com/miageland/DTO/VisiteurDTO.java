package com.miageland.DTO;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.miageland.model.Billet;
import lombok.Getter;

@Getter
public class VisiteurDTO {
    @JsonIgnoreProperties
    private String nom;
    @JsonIgnoreProperties
    private String prenom;
    @JsonIgnoreProperties
    private String mail;
    @JsonIgnoreProperties
    private Billet billetsVisiteur;
    public VisiteurDTO(String pNom, String pPrenom, String pMail, Billet pBilletsVisiteur){
        this.nom = pNom;
        this.prenom = pPrenom;
        this.mail = pMail;
        this.billetsVisiteur = pBilletsVisiteur;
    }

}
