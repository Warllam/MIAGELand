package com.miageland.DTO;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;

import java.time.LocalDate;
import java.util.Date;

@Getter
public class JaugeParcDTO {

    @JsonIgnoreProperties
    private LocalDate date;
    @JsonIgnoreProperties
    private int billetsVendus;
    @JsonIgnoreProperties
    private int jaugeMaximum;
    @JsonIgnoreProperties
    private int recetteJour;
    @JsonIgnoreProperties
    private int billetsAnnules;
    @JsonIgnoreProperties
    private int billetsReserveNonPaye;
}
