package com.miageland.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Date;

@Entity
@Getter
@Setter
public class JaugeParc {
    @Id
    private LocalDate date;
    private int billetsVendus;
    private int jaugeMaximum;
    private int recetteJour;
    private int billetsAnnules;
    private int billetsReserveNonPaye;

    public JaugeParc(LocalDate date){
        this.date = date;
        this.billetsVendus = 0;
        this.jaugeMaximum = 10;
        this.recetteJour = 0;
        this.billetsAnnules = 0;
        this.billetsReserveNonPaye = 0;
    }

    public JaugeParc() {
    }

}
