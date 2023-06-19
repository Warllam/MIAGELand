package com.miageland.exposition;

import com.miageland.DAO.VisiteurRepository;
import com.miageland.DTO.VisiteurDTO;
import com.miageland.exception.VisiteurNotFoundException;
import com.miageland.metier.VisiteurService;
import com.miageland.model.Visiteur;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
public class VisiteurController {

    private final VisiteurService visiteurService;

    public VisiteurController(VisiteurService visiteurService) {
        this.visiteurService = visiteurService;
    }

    @GetMapping("/visiteurs")
    List<Visiteur> all() {
        return this.visiteurService.getAllVisiteurs();
    }

    @GetMapping("/visiteurs/{id}")
    Visiteur one(@PathVariable Long id){
        return this.visiteurService.getVisiteurById(id);
    }

    @DeleteMapping("/visiteurs/{id}")
    void deletePatientUser(@PathVariable Long id) {
        this.visiteurService.deleteVisiteurById(id);
    }

    @PostMapping(value = "/visiteurs", consumes = "application/json;charset=UTF-8")
    Visiteur newVisiteur(@RequestBody VisiteurDTO newVisiteurParameter) {
        Visiteur visiteur = this.visiteurService.newVisiteur(newVisiteurParameter);
        return visiteur;
    }

}


