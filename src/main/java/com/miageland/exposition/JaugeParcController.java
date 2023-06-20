package com.miageland.exposition;

import com.miageland.DTO.JaugeParcDTO;
import com.miageland.exception.JaugeParcNotFoundException;
import com.miageland.metier.JaugeParcService;
import com.miageland.model.JaugeParc;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
/**
 * Contrôleur REST pour les opérations liées aux jauges de parc.
 */
@RestController
@RequestMapping("/jaugeParc")
public class JaugeParcController {

    private final JaugeParcService jaugeParcService;

    /**
     * Constructeur du contrôleur JaugeParcController.
     *
     * @param jaugeParcService le service de gestion des jauges de parc
     */
    public JaugeParcController(JaugeParcService jaugeParcService) {
        this.jaugeParcService = jaugeParcService;
    }

    /**
     * Récupère toutes les jauges de parc existantes.
     *
     * @return la liste de toutes les jauges de parc
     */
    @GetMapping
    List<JaugeParc> all() {
        return this.jaugeParcService.getAllJaugeParc();
    }

    /**
     * Récupère une jauge de parc par sa date.
     *
     * @param date la date de la jauge de parc souhaitée
     * @return la jauge de parc correspondante à la date
     * @throws JaugeParcNotFoundException si la jauge de parc n'a pas été trouvée
     */
    @GetMapping("/{date}")
    JaugeParc oneDate(@PathVariable LocalDate date) {
        JaugeParc jaugeParc = this.jaugeParcService.getJaugeParc(date);
        return jaugeParc;
    }

    /**
     * Crée une nouvelle jauge de parc.
     *
     * @param newJaugeParcParameter les paramètres de la nouvelle jauge de parc
     * @return la jauge de parc créée
     */
    @PostMapping(consumes = "application/json;charset=UTF-8")
    JaugeParc newJaugeParc(@RequestBody JaugeParcDTO newJaugeParcParameter) {
        JaugeParc jaugeParc = this.jaugeParcService.newJaugeParc(newJaugeParcParameter);
        return jaugeParc;
    }

    /**
     * Récupère le nombre de billets vendus pour une journée donnée.
     *
     * @param date la date de la journée souhaitée
     * @return le nombre de billets vendus pour la journée
     */
    @GetMapping("/ventesJour/{date}")
    int billetsVendusJour(@PathVariable LocalDate date) {
        return this.jaugeParcService.consulterVentesJour(date);
    }

    /**
     * Récupère la jauge de capacité maximale pour une journée donnée.
     *
     * @param date la date de la journée souhaitée
     * @return la jauge de capacité maximale pour la journée
     */
    @GetMapping("/jaugeMax/{date}")
    int jaugeCapaciteMax(@PathVariable LocalDate date) {
        return this.jaugeParcService.getJaugeParcMax(date);
    }

    /**
     * Met à jour la jauge de capacité maximale pour une journée donnée.
     *
     * @param date    la date de la journée à modifier
     * @param maJauge la nouvelle jauge de capacité maximale
     * @return la jauge de parc mise à jour
     */
    @PutMapping("/jaugeMax/{date}")
    public ResponseEntity<JaugeParc> updateJaugeParcCapacite(@PathVariable LocalDate date, @RequestParam int maJauge) {
        this.jaugeParcService.setJaugeParcMax(maJauge, date);
        return ResponseEntity.ok().build();
    }

    /**
     * Récupère le montant total de la recette pour une journée donnée.
     *
     * @param date la date de la journée souhaitée
     * @return le montant total de la recette pour la journée
     */
    @GetMapping("/recette/{date}")
    int recetteJour(@PathVariable LocalDate date) {
        return this.jaugeParcService.getAndCalculateRecetteJour(date);
    }

    /**
     * Récupère le nombre de billets annulés pour une journée donnée.
     *
     * @param date la date de la journée souhaitée
     * @return le nombre de billets annulés pour la journée
     */
    @GetMapping("/annulations/{date}")
    int consulterBilletsAnnules(@PathVariable LocalDate date) {
        return this.jaugeParcService.consulterBilletsAnnules(date);
    }

    /**
     * Récupère le nombre de billets réservés mais non payés pour une journée donnée.
     *
     * @param date la date de la journée souhaitée
     * @return le nombre de billets réservés mais non payés pour la journée
     */
    @GetMapping("/reserveNonPaye/{date}")
    int consulterBilletsReserveNonPaye(@PathVariable LocalDate date) {
        return this.jaugeParcService.consulterNbReserveNonPaye(date);
    }

    /**
     * Récupère le nombre de visites effectuées par un visiteur spécifique.
     *
     * @param idVisiteur l'ID du visiteur
     * @return le nombre de visites effectuées par le visiteur
     */
    @GetMapping("/nbVisiteVisiteur/{idVisiteur}")
    int consulterNbVisitesVisiteur(@PathVariable Long idVisiteur) {
        return this.jaugeParcService.nbVisiteVisiteur(idVisiteur);
    }
}
