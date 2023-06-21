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

    public VisiteurDTO(String pNom, String pPrenom, String pMail){
        this.nom = pNom;
        this.prenom = pPrenom;
        this.mail = pMail;
    }

}
