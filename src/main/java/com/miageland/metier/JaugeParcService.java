package com.miageland.metier;

import com.miageland.DAO.JaugeParcRepository;
import com.miageland.DAO.VisiteurRepository;
import com.miageland.DTO.JaugeParcDTO;
import com.miageland.exception.JaugeParcNotFoundException;
import com.miageland.exception.VisiteurNotFoundException;
import com.miageland.model.JaugeParc;
import com.miageland.model.Visiteur;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Date;

@Service
public class JaugeParcService {

    @Autowired
    private JaugeParcRepository jaugeParcRepository;

    @Autowired
    private VisiteurRepository visiteurRepository;

    public JaugeParcService(){

    }

    public JaugeParc newJaugeParc(JaugeParcDTO newJaugeParcParameter){
        JaugeParc jaugeParc = new JaugeParc(newJaugeParcParameter.getDate());
        this.jaugeParcRepository.save(jaugeParc);
        return jaugeParc;
    }

    public JaugeParc newJaugeParc(LocalDate date){
        JaugeParc jaugeParc = new JaugeParc(date);
        this.jaugeParcRepository.save(jaugeParc);
        return jaugeParc;
    }

    /**
     * @param date souhaitée
     * @return le nombre de billets vendus pour une date/jauge Parc
     */
    public int consulterVentesJour (LocalDate date) {
        JaugeParc jaugeParc = getJaugeParc(date);
        return jaugeParc.getBilletsVendus();
    }

    /**
     * Définir une jauge max pour le parc
     * @param nbVisiteurMax le nombre de visiteurs max
     * @param date la date pour la jauge
     */
    public JaugeParc setJaugeParcMax(int nbVisiteurMax, LocalDate date){
        JaugeParc jaugeParc = getJaugeParc(date);
        jaugeParc.setJaugeMaximum(nbVisiteurMax);
        return jaugeParc;
    }

    public int getJaugeParcMax(LocalDate date){
        JaugeParc jaugeParc = getJaugeParc(date);
        return jaugeParc.getJaugeMaximum();
    }

    /**
     * @param date souhaitée
     * @return les recettes du jour
     */
    public int recetteQuotidienne(LocalDate date) {
        JaugeParc jaugeParc = getJaugeParc(date);
        return jaugeParc.getRecetteJour();
    }

    /**
     * @param date souhaitée
     * @return le nombre de billets annulés sur la journée
     */
    public int consulterBilletsAnnules(LocalDate date){
        JaugeParc jaugeParc = getJaugeParc(date);
        return jaugeParc.getBilletsAnnules();
    }

    /**
     * @param date souhaitée
     * @return le nombre de reservations qui ne sont pas encore payées
     */
    public int consulterNbReserveNonPaye(LocalDate date){
        JaugeParc jaugeParc = getJaugeParc(date);
        return jaugeParc.getBilletsReserveNonPaye();
    }

    /**
     * @param idVisiteur l'id du visiteur
     * @return le nombre de visites visiteurs du jour
     */
    public int nbVisiteVisiteur(Long idVisiteur){
        Visiteur visiteur = visiteurRepository.getReferenceById(idVisiteur);
        if (visiteur == null){
            throw new VisiteurNotFoundException(idVisiteur);
        }
        return visiteur.getNbVisites();
    }

    public JaugeParc getJaugeParc(LocalDate date){
        JaugeParc jaugeParc = jaugeParcRepository.findById(date).orElseThrow(() -> new JaugeParcNotFoundException(date));
        //Si la jaugeParc n'existe pas on la créee
        if (jaugeParc == null) {
            jaugeParc = newJaugeParc(date);
        }
        return jaugeParc;
    }

    public void incrementerBilletsVendus(LocalDate date){
        JaugeParc jaugeParc = getJaugeParc(date);
        jaugeParc.setBilletsVendus(jaugeParc.getBilletsVendus() + 1);
        jaugeParc.setBilletsReserveNonPaye(jaugeParc.getBilletsReserveNonPaye() -1);
        this.jaugeParcRepository.save(jaugeParc);
    }

    public void incrementerBilletAnnules(LocalDate date){
        JaugeParc jaugeParc = getJaugeParc(date);
        jaugeParc.setBilletsAnnules(jaugeParc.getBilletsAnnules() + 1);
        this.jaugeParcRepository.save(jaugeParc);
    }

    public void incrementerBilletReserveNonPaye(LocalDate date){
        JaugeParc jaugeParc = getJaugeParc(date);
        jaugeParc.setBilletsReserveNonPaye(jaugeParc.getBilletsReserveNonPaye() + 1);
        this.jaugeParcRepository.save(jaugeParc);
    }

    public void decrementerBilletReserveNonPaye(LocalDate date){
        JaugeParc jaugeParc = getJaugeParc(date);
        jaugeParc.setBilletsReserveNonPaye(jaugeParc.getBilletsReserveNonPaye() - 1);
        this.jaugeParcRepository.save(jaugeParc);
    }
}
