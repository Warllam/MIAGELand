package com.miageland.DTO;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;

import java.util.Date;

@Getter
public class BilletDTO {
    @JsonIgnoreProperties
    private Date dateValidite;
    @JsonIgnoreProperties
    private float prix;
    @JsonIgnoreProperties
    private Date dateVente;
    @JsonIgnoreProperties
    private Long idVisiteur;
}
