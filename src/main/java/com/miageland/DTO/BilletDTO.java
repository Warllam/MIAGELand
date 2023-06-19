package com.miageland.DTO;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;

import java.time.LocalDate;
import java.util.Date;

@Getter
public class BilletDTO {
    @JsonIgnoreProperties
    private LocalDate dateValidite;
    @JsonIgnoreProperties
    private float prix;
    @JsonIgnoreProperties
    private Date dateVente;
    @JsonIgnoreProperties
    private Long idVisiteur;
}
