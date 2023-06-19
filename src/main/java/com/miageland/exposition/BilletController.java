package com.miageland.exposition;

import com.miageland.DAO.BilletRepository;
import com.miageland.DTO.BilletDTO;
import com.miageland.exception.BilletException;
import com.miageland.metier.BilletService;
import com.miageland.model.Billet;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Calendar;

@RestController
@RequestMapping("/reservations")
public class BilletController {

    private final BilletRepository repository;
    private final BilletService billetService;

    private BilletController(BilletRepository repository, BilletService billetService) {
        this.repository = repository;
        this.billetService = billetService;
    }

    @GetMapping
    List<Billet> all() {
        List<Billet> billets = repository.findAll();
        return billets;
    }

    @GetMapping("{idVisiteur}")
    List<Billet> allByIdVisiteur(Long idVisiteur) {
        List<Billet> billets = repository.findByVisiteurId(idVisiteur);
        return billets;
    }

    @PostMapping(consumes = "application/json;charset=UTF-8")
    Billet newBillet(@RequestBody BilletDTO newBilletParameter) throws ParseException {
        Billet billet = this.billetService.newBillet(newBilletParameter);
        return billet;
    }

    @DeleteMapping("{id}")
    String deleteBillet(@PathVariable Long id) {
        return this.billetService.deleteBillet(id);
    }

    @PutMapping("{id}/valider")
    ResponseEntity<String> validerBillet(@PathVariable Long id) {
        try {
            this.billetService.validerBillet(id);
            return ResponseEntity.ok("Billet validé avec succès.");
        } catch (BilletException e) {
            String errorMessage = "Erreur lors de la validation du billet : " + e.getMessage();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
        }
    }
}
