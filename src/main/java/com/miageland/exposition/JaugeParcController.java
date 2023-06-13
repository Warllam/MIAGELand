package com.miageland.exposition;

import com.miageland.DAO.JaugeParcRepository;
import com.miageland.DTO.BilletDTO;
import com.miageland.DTO.JaugeParcDTO;
import com.miageland.metier.JaugeParcService;
import com.miageland.model.Billet;
import com.miageland.model.JaugeParc;
import com.miageland.model.Visiteur;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
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
     *
     * @return la liste de toutes les JaugeParc
     */
    @GetMapping
    List<JaugeParc> all() {
        List<JaugeParc> jaugeParcs = repository.findAll();
        return jaugeParcs;
    }

    /**
     *
     * @param date de la jauge parc voulue
     * @return la JaugeParc
     */
    @GetMapping("/{date}")
    JaugeParc oneDate(@PathVariable Date date){
        return this.repository.getReferenceById(date);
    }

    /*
    /**
     *
     * @param newJaugeParcParameter les paramètres de la Jauge Parc
     * @return JaugeParc créée
     * @throws ParseException
     */
    /*
    @PostMapping(consumes = "application/json;charset=UTF-8")
    JaugeParc newJaugeParc(@RequestBody JaugeParcDTO newJaugeParcParameter) throws ParseException {
        JaugeParc jaugeParc = this.jaugeParcService.newJaugeParc(newJaugeParcParameter);
        return jaugeParc;
    }

    @GetMapping("/{date}/ventesJour")
    int billetsVendusJour(@PathVariable Date date){
        return this.repository.getReferenceById(date).getBilletsVendus();
    }
    */

    //nb visites visiteurs


    //recup nb billets vendus
    // Get et set jauge max
    //get recette jour
    //get nb billets annul
    //get reserv non paye


    //return tous les jauge parc
    //return une jauge parc selon date
    //creation jauge parc


}
