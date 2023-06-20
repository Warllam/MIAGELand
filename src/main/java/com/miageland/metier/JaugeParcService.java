package com.miageland.metier;

import com.miageland.DAO.JaugeParcRepository;
import com.miageland.DTO.JaugeParcDTO;
import com.miageland.exception.VisiteurNotFoundException;
import com.miageland.model.Billet;
import com.miageland.model.EtatBillet;
import com.miageland.model.JaugeParc;
import com.miageland.model.Visiteur;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
/**
 * Service de gestion des jauges de parc.
 */
@Service
public class JaugeParcService {

    @Autowired
    private JaugeParcRepository jaugeParcRepository;

    @Autowired
    private VisiteurService visiteurService;

    @Autowired
    private BilletService billetService;

    /**
     * Crée une nouvelle jauge de parc à partir des paramètres fournis.
     *
     * @param newJaugeParcParameter les paramètres de la nouvelle jauge de parc
     * @return la jauge de parc créée
     */
    public JaugeParc newJaugeParc(JaugeParcDTO newJaugeParcParameter) {
        JaugeParc jaugeParc = new JaugeParc(newJaugeParcParameter.getDate());
        this.jaugeParcRepository.save(jaugeParc);
        return jaugeParc;
    }

    /**
     * Crée une nouvelle jauge de parc pour une date spécifiée.
     *
     * @param date la date de la nouvelle jauge de parc
     * @return la jauge de parc créée
     */
    public JaugeParc newJaugeParc(LocalDate date) {
        JaugeParc jaugeParc = new JaugeParc(date);
        this.jaugeParcRepository.save(jaugeParc);
        return jaugeParc;
    }

    /**
     * Consulte le nombre de billets vendus pour une date donnée.
     *
     * @param date la date pour laquelle on souhaite consulter les ventes de billets
     * @return le nombre de billets vendus pour la date donnée
     */
    public int consulterVentesJour(LocalDate date) {
        JaugeParc jaugeParc = getJaugeParc(date);
        return jaugeParc.getBilletsVendus();
    }

    /**
     * Récupère toutes les jauges de parc existantes.
     *
     * @return la liste de toutes les jauges de parc
     */
    public List<JaugeParc> getAllJaugeParc() {
        return this.jaugeParcRepository.findAll();
    }

    /**
     * Définit la jauge maximale de capacité pour une journée donnée.
     *
     * @param nbVisiteurMax le nombre de visiteurs maximum
     * @param date          la date de la jauge de parc à modifier
     * @return la jauge de parc mise à jour
     */
    public JaugeParc setJaugeParcMax(int nbVisiteurMax, LocalDate date) {
        JaugeParc jaugeParc = getJaugeParc(date);
        jaugeParc.setJaugeMaximum(nbVisiteurMax);
        this.jaugeParcRepository.save(jaugeParc);
        return jaugeParc;
    }

    /**
     * Récupère la jauge maximale de capacité pour une journée donnée.
     *
     * @param date la date de la jauge de parc souhaitée
     * @return la jauge maximale de capacité pour la journée
     */
    public int getJaugeParcMax(LocalDate date) {
        JaugeParc jaugeParc = getJaugeParc(date);
        return jaugeParc.getJaugeMaximum();
    }

    /**
     * Récupère le nombre de billets annulés pour une journée donnée.
     *
     * @param date la date de la journée souhaitée
     * @return le nombre de billets annulés pour la journée
     */
    public int consulterBilletsAnnules(LocalDate date) {
        JaugeParc jaugeParc = getJaugeParc(date);
        return jaugeParc.getBilletsAnnules();
    }

    /**
     * Récupère le nombre de réservations non payées pour une journée donnée.
     *
     * @param date la date de la journée souhaitée
     * @return le nombre de réservations non payées pour la journée
     */
    public int consulterNbReserveNonPaye(LocalDate date) {
        JaugeParc jaugeParc = getJaugeParc(date);
        return jaugeParc.getBilletsReserveNonPaye();
    }

    /**
     * Récupère le nombre de visites effectuées par un visiteur spécifique.
     *
     * @param idVisiteur l'ID du visiteur
     * @return le nombre de visites effectuées par le visiteur
     */
    public int nbVisiteVisiteur(Long idVisiteur) {
        Visiteur visiteur = visiteurService.getVisiteurById(idVisiteur);
        if (visiteur == null) {
            throw new VisiteurNotFoundException(idVisiteur);
        }
        return visiteur.getNbVisites();
    }

    /**
     * Récupère la jauge de parc pour une date spécifiée. Si la jauge de parc n'existe pas, elle est créée.
     *
     * @param date la date de la jauge de parc
     * @return la jauge de parc correspondante
     */
    public JaugeParc getJaugeParc(LocalDate date) {
        JaugeParc jaugeParc = jaugeParcRepository.findById(date).orElse(null);
        if (jaugeParc == null) {
            jaugeParc = newJaugeParc(date);
        }
        return jaugeParc;
    }

    /**
     * Incrémente le nombre de billets vendus pour une journée donnée.
     *
     * @param date la date de la journée
     */
    public void incrementerBilletsVendus(LocalDate date) {
        JaugeParc jaugeParc = getJaugeParc(date);
        jaugeParc.setBilletsVendus(jaugeParc.getBilletsVendus() + 1);
        jaugeParc.setBilletsReserveNonPaye(jaugeParc.getBilletsReserveNonPaye() - 1);
        this.jaugeParcRepository.save(jaugeParc);
    }

    /**
     * Incrémente le nombre de billets annulés pour une journée donnée.
     *
     * @param date la date de la journée
     */
    public void incrementerBilletAnnules(LocalDate date) {
        JaugeParc jaugeParc = getJaugeParc(date);
        jaugeParc.setBilletsAnnules(jaugeParc.getBilletsAnnules() + 1);
        this.jaugeParcRepository.save(jaugeParc);
    }

    /**
     * Incrémente le nombre de billets réservés mais non payés pour une journée donnée.
     *
     * @param date la date de la journée
     */
    public void incrementerBilletReserveNonPaye(LocalDate date) {
        JaugeParc jaugeParc = getJaugeParc(date);
        jaugeParc.setBilletsReserveNonPaye(jaugeParc.getBilletsReserveNonPaye() + 1);
        this.jaugeParcRepository.save(jaugeParc);
    }

    /**
     * Décrémente le nombre de billets réservés mais non payés pour une journée donnée.
     *
     * @param date la date de la journée
     */
    public void decrementerBilletReserveNonPaye(LocalDate date) {
        JaugeParc jaugeParc = getJaugeParc(date);
        jaugeParc.setBilletsReserveNonPaye(jaugeParc.getBilletsReserveNonPaye() - 1);
        this.jaugeParcRepository.save(jaugeParc);
    }

    /**
     * Calcule et récupère le montant total de la recette pour une journée donnée.
     *
     * @param date la date de la journée
     * @return le montant total de la recette pour la journée
     */
    public int getAndCalculateRecetteJour(LocalDate date) {
        JaugeParc jaugeParc = getJaugeParc(date);
        int recetteJour = 0;

        List<Billet> billets = billetService.getAllBilletsByDateValidite(date);
        for (Billet billet : billets) {
            if (billet.getEtatBillet() == EtatBillet.VALIDE) {
                recetteJour += billet.getPrix();
            }
        }

        jaugeParc.setRecetteJour(recetteJour);
        return jaugeParc.getRecetteJour();
    }
}
