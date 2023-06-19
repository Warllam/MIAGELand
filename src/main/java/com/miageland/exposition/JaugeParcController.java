package com.miageland.exposition;

import com.miageland.DAO.JaugeParcRepository;
import com.miageland.DTO.JaugeParcDTO;
import com.miageland.exception.JaugeParcNotFoundException;
import com.miageland.metier.JaugeParcService;
import com.miageland.model.JaugeParc;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/jaugeParc")
public class JaugeParcController {

    private final JaugeParcRepository repository;
    private final JaugeParcService jaugeParcService;

    public JaugeParcController(JaugeParcRepository repository, JaugeParcService jaugeParcService) {
        this.repository = repository;
        this.jaugeParcService = jaugeParcService;
    }

    /**
     * @return la liste de toutes les JaugeParc
     */
    @GetMapping
    List<JaugeParc> all() {
        List<JaugeParc> jaugeParcs = repository.findAll();
        return jaugeParcs;
    }

    /**
     * @param date de la jauge parc voulue
     * @return la JaugeParc
     */
    @GetMapping("/{date}")
    JaugeParc oneDate(@PathVariable LocalDate date){
        JaugeParc jaugeParc = repository.findById(date).orElseThrow(() -> new JaugeParcNotFoundException(date));
        return jaugeParc;
    }


    /**
     * @param newJaugeParcParameter les paramètres de la Jauge Parc
     * @return JaugeParc créée
     */
    @PostMapping(consumes = "application/json;charset=UTF-8")
    JaugeParc newJaugeParc(@RequestBody JaugeParcDTO newJaugeParcParameter) {
        JaugeParc jaugeParc = this.jaugeParcService.newJaugeParc(newJaugeParcParameter);
        return jaugeParc;
    }



    /**
     * @param date souhaitée
     * @return le nombre de billets vendus sur le jour souhaité
     */
    @GetMapping("/ventesJour/{date}")
    int billetsVendusJour(@PathVariable LocalDate date){
        return this.jaugeParcService.consulterVentesJour(date);
    }

    /**
     *
     * @param date du jour souhaité
     * @return la jauge de capacité max pour ce jour
     */
    @GetMapping("/jaugeMax/{date}")
    int jaugeCapaciteMax(@PathVariable LocalDate date){
        return this.jaugeParcService.getJaugeParcMax(date);
    }

    /**
     *
     * @param date du jour à modifier
     * @param maJauge nouvelle jauge max
     */
    @PutMapping("/jaugeMax/{date}")
    public ResponseEntity<JaugeParc> updateJaugeParcCapacite(@PathVariable LocalDate date, @RequestBody int maJauge) {
        JaugeParc jaugeParc = repository.findById(date)
                .orElseThrow(() -> new JaugeParcNotFoundException(date));

        JaugeParc updatedJaugeParc = jaugeParcService.setJaugeParcMax(maJauge,date);
        return ResponseEntity.ok(updatedJaugeParc);
    }

    /**
     *
     * @param date du jour souhaité
     * @return les recettes du jour
     */
    @GetMapping("/recette/{date}")
    int recetteJour(@PathVariable LocalDate date){
        return this.jaugeParcService.recetteQuotidienne(date);
    }

    /**
     *
     * @param date du jour souhaité
     * @return le nombre de billets annulés du jour
     */
    @GetMapping("/annulations/{date}")
    int consulterBilletsAnnules(@PathVariable LocalDate date){
        return this.jaugeParcService.consulterBilletsAnnules(date);
    }

    /**
     *
     * @param date du jour souhaité
     * @return le nombre de billets réservés mais non payés
     */
    @GetMapping("/reserveNonPaye/{date}")
    int consulterBilletsReserveNonPaye(@PathVariable LocalDate date){
        return this.jaugeParcService.consulterNbReserveNonPaye(date);
    }

    /**
     *
     * @param idVisiteur du visiteur
     * @return le nombre de visites qu'il a effectuées
     */
    @GetMapping("/nbVisiteVisiteur/{idVisiteur}")
    int consulterNbVisitesVisiteur(@PathVariable Long idVisiteur){
        return this.jaugeParcService.nbVisiteVisiteur(idVisiteur);
    }
}
