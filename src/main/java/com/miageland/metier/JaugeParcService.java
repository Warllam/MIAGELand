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

    /**
     * @param date souhaitée
     * @return le nombre de billets vendus pour une date/jauge Parc
     */
    public int consulterVentesJour (Date date) {
        JaugeParc jaugeParc = jaugeParcRepository.findById(date).orElseThrow(() -> new JaugeParcNotFoundException(date));
        return jaugeParc.getBilletsVendus();
    }

    /**
     * Définir une jauge max pour le parc
     * @param nbVisiteurMax le nombre de visiteurs max
     * @param date la date pour la jauge
     */
    public JaugeParc setJaugeParcMax(int nbVisiteurMax, Date date){
        JaugeParc jaugeParc = jaugeParcRepository.findById(date).orElseThrow(() -> new JaugeParcNotFoundException(date));
        jaugeParc.setJaugeMaximum(nbVisiteurMax);
        return jaugeParc;
    }

    public int getJaugeParcMax(Date date){
        JaugeParc jaugeParc = jaugeParcRepository.findById(date).orElseThrow(() -> new JaugeParcNotFoundException(date));
        return jaugeParc.getJaugeMaximum();
    }

    /**
     * @param date souhaitée
     * @return les recettes du jour
     */
    public int recetteQuotidienne(Date date) {
        JaugeParc jaugeParc = jaugeParcRepository.findById(date).orElseThrow(() -> new JaugeParcNotFoundException(date));
        return jaugeParc.getRecetteJour();
    }

    /**
     * @param date souhaitée
     * @return le nombre de billets annulés sur la journée
     */
    public int consulterBilletsAnnules(Date date){
        JaugeParc jaugeParc = jaugeParcRepository.findById(date).orElseThrow(() -> new JaugeParcNotFoundException(date));
        return jaugeParc.getBilletsAnnules();
    }

    /**
     * @param date souhaitée
     * @return le nombre de reservations qui ne sont pas encore payées
     */
    public int consulterNbReserveNonPaye(Date date){
        JaugeParc jaugeParc = jaugeParcRepository.findById(date).orElseThrow(() -> new JaugeParcNotFoundException(date));
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
        return visiteur.nombreVisites();
    }
}
