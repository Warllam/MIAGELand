package com.miageland.metier;

import com.miageland.DAO.AttractionRepository;
import com.miageland.DAO.BilletRepository;
import com.miageland.DAO.JaugeParcRepository;
import com.miageland.DAO.VisiteurRepository;
import com.miageland.DTO.BilletDTO;
import com.miageland.exception.BilletException;
import com.miageland.model.Billet;
import com.miageland.model.EtatBillet;
import com.miageland.model.Visiteur;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

@Service
public class BilletService {

    @Autowired
    private BilletRepository billetRepository;

    @Autowired
    private VisiteurRepository visiteurRepository;
    @Autowired
    private JaugeParcService jaugeParcService;

    public BilletService(){}

    public Billet newBillet(BilletDTO newBilletParameter){
        Visiteur visiteur = visiteurRepository.getReferenceById(newBilletParameter.getIdVisiteur());
        if(visiteur == null){
            throw new BilletException("Visiteur non trouvé");
        }
        Billet billet = new Billet(newBilletParameter.getDateValidite(), newBilletParameter.getPrix(), newBilletParameter.getDateVente(), visiteur);
        this.billetRepository.save(billet);
        this.jaugeParcService.incrementerBilletReserveNonPaye(billet.getDateValidite());
        return billet;
    }

    public String annulerBillet(Long idBillet){
        Billet billet = billetRepository.findById(idBillet).orElseThrow(() -> new BilletException("Could not find billet " + idBillet));

        // Récupérer la date actuelle et enlever 7 jours
        LocalDate dateActuelle = LocalDate.now().minusDays(7);

        if (dateActuelle.isAfter(billet.getDateValidite())) {
            throw new IllegalStateException("Impossible de supprimer un billet à moins de 7 jours de la date de validité");
        } else {
            String message;
            if(billet.isEstPaye()){
                message = "Billet annulé et remboursé : " + billet.getPrix();
                this.jaugeParcService.incrementerBilletAnnules(billet.getDateValidite());
            }else{
                message = "Billet non payé annulé";
                this.jaugeParcService.decrementerBilletReserveNonPaye(billet.getDateValidite());
            }
            billet.setEtatBillet(EtatBillet.ANNULE);
            this.billetRepository.save(billet);

            return message;
        }
    }

    public String payerBillet(Long idBillet){
        Billet billet = billetRepository.findById(idBillet).orElseThrow(() -> new BilletException("Could not find billet " + idBillet));
        if(billet.isEstPaye()){
            throw new IllegalStateException("Le billet est déjà payé");
        }else{
            billet.setEstPaye(true);
            billet.setEtatBillet(EtatBillet.VALIDE);
            this.billetRepository.save(billet);
            this.jaugeParcService.incrementerBilletsVendus(billet.getDateValidite());

            return "Billet payé";
        }
    }

    public Billet validerBillet(Long idBillet){
        Billet billet = billetRepository.findById(idBillet).orElseThrow(() -> new BilletException("Could not find billet " + idBillet));
        LocalDate localDate = LocalDate.now();

        if (billet.getEtatBillet() != EtatBillet.VALIDE) {
            throw new BilletException("Billet non valide : " + billet.getEtatBillet());
        } else {
            if (billet.getDateValidite().isBefore(localDate)) {
                throw new BilletException("Billet périmé");
            /*} else {
                if (this.jaugeParcService.consulterVentesJour(localDate) >= this.jaugeParcService.getJaugeParcMax(localDate)) {
                     throw new BilletException("La jauge du parc est atteinte");
                }*/
            }
        }

        billet.setEtatBillet(EtatBillet.UTILISE);

        this.jaugeParcService.incrementerBilletsVendus(localDate);
        this.billetRepository.save(billet);
        return billet;
    }


}
