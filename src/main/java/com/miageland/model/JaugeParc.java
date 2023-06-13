package com.miageland.model;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class JaugeParc {
    @Id
    private Date date;
    private int billetsVendus;
    private int jaugeMaximum;
    private int recetteJour;
    private int billetsAnnules;
    private int billetsReserveNonPaye;

    public JaugeParc(Date date){
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
