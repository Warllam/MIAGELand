package com.miageland.exposition;

import com.miageland.DAO.BilletRepository;
import com.miageland.DTO.BilletDTO;
import com.miageland.MyUtils;
import com.miageland.exception.BilletException;
import com.miageland.metier.BilletService;
import com.miageland.model.Billet;
import com.miageland.model.JaugeParc;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.text.ParseException;
import java.util.List;
/**
 * Contrôleur REST pour les opérations liées aux billets.
 */
@RestController
@RequestMapping("/billets")
public class BilletController {

    private final BilletService billetService;

    /**
     * Constructeur du contrôleur BilletController.
     *
     * @param billetService le service de gestion des billets
     */
    public BilletController(BilletService billetService) {
        this.billetService = billetService;
    }

    /**
     * Récupère tous les billets.
     *
     * @return la liste de tous les billets existants
     */
    @GetMapping
    ResponseEntity<List<Billet>> all(HttpSession session){
        try {
            MyUtils.checkUserRoleEmploye(session);
            return ResponseEntity.ok(this.billetService.getAllBillets());
        } catch (ResponseStatusException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }

    /**
     * Récupère un billet par son ID.
     *
     * @param id l'ID du billet à récupérer
     * @return le billet correspondant à l'ID
     * @throws BilletException si le billet n'a pas été trouvé
     */
    @GetMapping("{id}")
    ResponseEntity<Billet> getBilletById(@PathVariable Long id) {
        try {
            Billet billet = this.billetService.getBilletById(id);
            return ResponseEntity.ok(billet);
        } catch (BilletException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Récupère tous les billets d'un visiteur spécifique.
     *
     * @param idVisiteur l'ID du visiteur
     * @return la liste des billets du visiteur
     */
    @GetMapping("user/{idVisiteur}")
    List<Billet> allByIdVisiteur(@PathVariable Long idVisiteur) {
        return this.billetService.getAllBilletsByVisiteur(idVisiteur);
    }

    /**
     * Crée un nouveau billet.
     *
     * @param newBilletParameter les paramètres du billet à créer
     * @return le billet créé
     * @throws ParseException   en cas d'erreur lors de l'analyse des dates
     * @throws BilletException  si les paramètres du billet sont invalides
     */
    @PostMapping(consumes = "application/json;charset=UTF-8")
    ResponseEntity<?> newBillet(@RequestBody BilletDTO newBilletParameter, HttpSession session) throws ParseException {
        try {
            MyUtils.checkUserRoleVisiteur(session);
            Billet billet = this.billetService.newBillet(newBilletParameter);
            return ResponseEntity.ok(billet);
        } catch (BilletException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }catch (ResponseStatusException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }

    /**
     * Annule un billet.
     *
     * @param id l'ID du billet à annuler
     * @return le message de confirmation d'annulation
     * @throws BilletException          si le billet n'a pas été trouvé
     * @throws IllegalStateException si l'état du billet est invalide pour l'annulation
     */
    @PutMapping("{id}/annuler")
    ResponseEntity<String> deleteBillet(@PathVariable Long id, HttpSession session) {
        try {
            MyUtils.checkUserRoleVisiteur(session);
            String message = this.billetService.annulerBillet(id);
            return ResponseEntity.ok(message);
        } catch (BilletException e) {
            return ResponseEntity.notFound().build();
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (ResponseStatusException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
         }
    }

    /**
     * Effectue le paiement d'un billet.
     *
     * @param id l'ID du billet à payer
     * @return le message de confirmation de paiement
     * @throws BilletException          si le billet n'a pas été trouvé
     * @throws IllegalStateException si le billet est déjà payé
     */
    @PutMapping("{id}/paiement")
    ResponseEntity<String> payerBillet(@PathVariable Long id, HttpSession session) {
        try {
            MyUtils.checkUserRoleVisiteur(session);
            String response = this.billetService.payerBillet(id);
            return ResponseEntity.ok(response);
        } catch (BilletException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (ResponseStatusException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }

    /**
     * Valide un billet.
     *
     * @param id l'ID du billet à valider
     * @return le message de confirmation de validation
     * @throws BilletException si le billet n'a pas été trouvé ou s'il n'est pas valide
     */
    @PutMapping("{id}/valider")
    ResponseEntity<String> validerBillet(@PathVariable Long id, HttpSession session) {
        try {
            MyUtils.checkUserRoleEmploye(session);
            this.billetService.validerBillet(id);
            return ResponseEntity.ok("Billet validé avec succès.");
        } catch (BilletException e) {
            String errorMessage = "Erreur lors de la validation du billet : " + e.getMessage();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
        } catch (ResponseStatusException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }
}
