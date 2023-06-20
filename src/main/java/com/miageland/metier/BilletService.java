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
import java.util.List;

@Service
public class BilletService {

    @Autowired
    private BilletRepository billetRepository;

    @Autowired
    private VisiteurService visiteurService;
    @Autowired
    private JaugeParcService jaugeParcService;

    /**
     * Crée un nouveau billet en fonction des informations fournies.
     *
     * @param newBilletParameter les paramètres du nouveau billet
     * @return le billet créé
     * @throws BilletException si le visiteur associé n'est pas trouvé
     */
    public Billet newBillet(BilletDTO newBilletParameter) {
        Visiteur visiteur = visiteurService.getVisiteurById(newBilletParameter.getIdVisiteur());
        if (visiteur == null) {
            throw new BilletException("Visiteur non trouvé");
        }
        Billet billet = new Billet(newBilletParameter.getDateValidite(), newBilletParameter.getPrix(), newBilletParameter.getDateVente(), visiteur);
        this.billetRepository.save(billet);
        this.jaugeParcService.incrementerBilletReserveNonPaye(billet.getDateValidite());
        return billet;
    }

    /**
     * Annule un billet en fonction de son identifiant.
     *
     * @param idBillet l'identifiant du billet à annuler
     * @return le message indiquant l'état de l'annulation
     * @throws BilletException si le billet n'est pas trouvé ou s'il est impossible de le supprimer
     */
    public String annulerBillet(Long idBillet) {
        Billet billet = billetRepository.findById(idBillet).orElseThrow(() -> new BilletException("Could not find billet " + idBillet));

        // Récupérer la date actuelle et enlever 7 jours
        LocalDate dateActuelle = LocalDate.now().minusDays(7);

        if (dateActuelle.isAfter(billet.getDateValidite())) {
            throw new IllegalStateException("Impossible de supprimer un billet à moins de 7 jours de la date de validité");
        } else {
            String message;
            if (billet.isEstPaye()) {
                message = "Billet annulé et remboursé : " + billet.getPrix();
                this.jaugeParcService.incrementerBilletAnnules(billet.getDateValidite());
            } else {
                message = "Billet non payé annulé";
                this.jaugeParcService.decrementerBilletReserveNonPaye(billet.getDateValidite());
            }
            billet.setEtatBillet(EtatBillet.ANNULE);
            this.billetRepository.save(billet);

            return message;
        }
    }

    /**
     * Effectue le paiement d'un billet en fonction de son identifiant.
     *
     * @param idBillet l'identifiant du billet à payer
     * @return le message indiquant le succès du paiement
     * @throws IllegalStateException si le billet est déjà payé
     * @throws BilletException      si le billet n'est pas trouvé
     */
    public String payerBillet(Long idBillet) {
        Billet billet = billetRepository.findById(idBillet).orElseThrow(() -> new BilletException("Could not find billet " + idBillet));
        if (billet.isEstPaye()) {
            throw new IllegalStateException("Le billet est déjà payé");
        } else {
            billet.setEstPaye(true);
            billet.setEtatBillet(EtatBillet.VALIDE);
            this.billetRepository.save(billet);
            this.jaugeParcService.incrementerBilletsVendus(billet.getDateValidite());

            return "Billet payé";
        }
    }

    /**
     * Valide un billet en fonction de son identifiant.
     *
     * @param idBillet l'identifiant du billet à valider
     * @return le billet validé
     * @throws BilletException si le billet n'est pas valide (non payé, périmé ou jauge du parc atteinte)
     */
    public Billet validerBillet(Long idBillet) {
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

    /**
     * Récupère tous les billets.
     *
     * @return la liste de tous les billets
     */
    public List<Billet> getAllBillets() {
        return this.billetRepository.findAll();
    }

    /**
     * Récupère un billet en fonction de son identifiant.
     *
     * @param idBillet l'identifiant du billet
     * @return le billet correspondant
     * @throws BilletException si le billet n'est pas trouvé
     */
    public Billet getBilletById(Long idBillet) {
        return this.billetRepository.findById(idBillet).orElseThrow(() -> new BilletException("Could not find billet " + idBillet));
    }

    /**
     * Récupère tous les billets d'un visiteur en fonction de son identifiant.
     *
     * @param idVisiteur l'identifiant du visiteur
     * @return la liste de tous les billets du visiteur
     */
    public List<Billet> getAllBilletsByVisiteur(Long idVisiteur) {
        return this.billetRepository.findByVisiteurId(idVisiteur);
    }

    /**
     * Récupère tous les billets avec une date de validité donnée.
     *
     * @param dateValidite la date de validité
     * @return la liste de tous les billets correspondants
     */
    public List<Billet> getAllBilletsByDateValidite(LocalDate dateValidite) {
        return this.billetRepository.findByDateValidite(dateValidite);
    }
}
