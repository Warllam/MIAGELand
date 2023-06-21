package com.miageland.exposition;

import com.miageland.DTO.JaugeParcDTO;
import com.miageland.MyUtils;
import com.miageland.exception.JaugeParcNotFoundException;
import com.miageland.metier.JaugeParcService;
import com.miageland.model.JaugeParc;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

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
    ResponseEntity<List<JaugeParc>> all(HttpSession session) {
        try {
            MyUtils.checkUserRoleGerant(session);
            return ResponseEntity.ok(this.jaugeParcService.getAllJaugeParc());
        } catch (ResponseStatusException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }

    /**
     * Récupère une jauge de parc par sa date.
     *
     * @param date la date de la jauge de parc souhaitée
     * @return la jauge de parc correspondante à la date
     * @throws JaugeParcNotFoundException si la jauge de parc n'a pas été trouvée
     */
    @GetMapping("/{date}")
    ResponseEntity<JaugeParc> oneDate(@PathVariable LocalDate date, HttpSession session) {
        try {
            MyUtils.checkUserRoleGerant(session);
            JaugeParc jaugeParc = this.jaugeParcService.getJaugeParc(date);
            return ResponseEntity.ok(jaugeParc);
        } catch (ResponseStatusException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }

    /**
     * Crée une nouvelle jauge de parc.
     *
     * @param newJaugeParcParameter les paramètres de la nouvelle jauge de parc
     * @return la jauge de parc créée
     */
    @PostMapping(consumes = "application/json;charset=UTF-8")
    ResponseEntity<JaugeParc> newJaugeParc(@RequestBody JaugeParcDTO newJaugeParcParameter, HttpSession session) {
        try {
            MyUtils.checkUserRoleGerant(session);
            JaugeParc jaugeParc = this.jaugeParcService.newJaugeParc(newJaugeParcParameter);
            return ResponseEntity.ok(jaugeParc);
        } catch (ResponseStatusException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }

    /**
     * Récupère le nombre de billets vendus pour une journée donnée.
     *
     * @param date la date de la journée souhaitée
     * @return le nombre de billets vendus pour la journée
     */
    @GetMapping("/ventesJour/{date}")
    ResponseEntity<Integer> billetsVendusJour(@PathVariable LocalDate date, HttpSession session) {
        try {
            MyUtils.checkUserRoleGerant(session);
            int billetsVendus = this.jaugeParcService.consulterVentesJour(date);
            return ResponseEntity.ok(billetsVendus);
        } catch (ResponseStatusException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }

    /**
     * Récupère la jauge de capacité maximale pour une journée donnée.
     *
     * @param date la date de la journée souhaitée
     * @return la jauge de capacité maximale pour la journée
     */
    @GetMapping("/jaugeMax/{date}")
    ResponseEntity<Integer> jaugeCapaciteMax(@PathVariable LocalDate date, HttpSession session) {
        try {
            MyUtils.checkUserRoleGerant(session);
            return ResponseEntity.ok(this.jaugeParcService.getJaugeParcMax(date));
        } catch (ResponseStatusException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }

    /**
     * Met à jour la jauge de capacité maximale pour une journée donnée.
     *
     * @param date    la date de la journée à modifier
     * @param maJauge la nouvelle jauge de capacité maximale
     * @return la jauge de parc mise à jour
     */
    @PutMapping("/jaugeMax/{date}")
    public ResponseEntity<JaugeParc> updateJaugeParcCapacite(@PathVariable LocalDate date, @RequestParam int maJauge, HttpSession session) {
        try {
            MyUtils.checkUserRoleGerant(session);
            this.jaugeParcService.setJaugeParcMax(maJauge, date);
            return ResponseEntity.ok().build();
        } catch (ResponseStatusException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }


    /**
     * Récupère le montant total de la recette pour une journée donnée.
     *
     * @param date la date de la journée souhaitée
     * @return le montant total de la recette pour la journée
     */
    @GetMapping("/recette/{date}")
    public ResponseEntity<Integer> recetteJour(@PathVariable LocalDate date, HttpSession session) {
        try {
            MyUtils.checkUserRoleGerant(session);
            int recette = this.jaugeParcService.getAndCalculateRecetteJour(date);
            return ResponseEntity.ok(recette);
        } catch (ResponseStatusException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }

    /**
     * Récupère le nombre de billets annulés pour une journée donnée.
     *
     * @param date la date de la journée souhaitée
     * @return le nombre de billets annulés pour la journée
     */
    @GetMapping("/annulations/{date}")
    public ResponseEntity<Integer> consulterBilletsAnnules(@PathVariable LocalDate date, HttpSession session) {
        try {
            MyUtils.checkUserRoleGerant(session);
            int billetsAnnules = this.jaugeParcService.consulterBilletsAnnules(date);
            return ResponseEntity.ok(billetsAnnules);
        } catch (ResponseStatusException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }


    /**
     * Récupère le nombre de billets réservés mais non payés pour une journée donnée.
     *
     * @param date la date de la journée souhaitée
     * @return le nombre de billets réservés mais non payés pour la journée
     */
    @GetMapping("/reserveNonPaye/{date}")
    public ResponseEntity<Integer> consulterBilletsReserveNonPaye(@PathVariable LocalDate date, HttpSession session) {
        try {
            MyUtils.checkUserRoleGerant(session);
            int billetsReserveNonPaye = this.jaugeParcService.consulterNbReserveNonPaye(date);
            return ResponseEntity.ok(billetsReserveNonPaye);
        } catch (ResponseStatusException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }


    /**
     * Récupère le nombre de visites effectuées par un visiteur spécifique.
     *
     * @param idVisiteur l'ID du visiteur
     * @return le nombre de visites effectuées par le visiteur
     */
    @GetMapping("/nbVisiteVisiteur/{idVisiteur}")
    public ResponseEntity<Integer> consulterNbVisitesVisiteur(@PathVariable Long idVisiteur, HttpSession session) {
        try {
            MyUtils.checkUserRoleGerant(session);
            int nbVisites = this.jaugeParcService.nbVisiteVisiteur(idVisiteur);
            return ResponseEntity.ok(nbVisites);
        } catch (ResponseStatusException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }
}
