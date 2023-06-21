package com.miageland.exposition;

import com.miageland.DTO.EmployeDTO;
import com.miageland.DTO.VisiteurDTO;
import com.miageland.metier.VisiteurService;
import com.miageland.model.Employe;
import com.miageland.model.Visiteur;
import jakarta.persistence.EntityExistsException;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

/**
 * Contrôleur REST pour gérer les visiteurs.
 */
@RestController
public class VisiteurController {

    private final VisiteurService visiteurService;

    public VisiteurController(VisiteurService visiteurService) {
        this.visiteurService = visiteurService;
    }

    /**
     * Récupère la liste de tous les visiteurs.
     *
     * @return la liste de tous les visiteurs
     */
    @GetMapping("/visiteurs")
    List<Visiteur> all() {
        return this.visiteurService.getAllVisiteurs();
    }

    /**
     * Récupère un visiteur spécifié par son identifiant.
     *
     * @param id l'identifiant du visiteur
     * @return le visiteur correspondant à l'identifiant
     */
    @GetMapping("/visiteurs/{id}")
    Visiteur one(@PathVariable Long id) {
        return this.visiteurService.getVisiteurById(id);
    }

    /**
     * Supprime un visiteur spécifié par son identifiant.
     *
     * @param id l'identifiant du visiteur à supprimer
     */
    @DeleteMapping("/visiteurs/{id}")
    void deletePatientUser(@PathVariable Long id) {
        this.visiteurService.deleteVisiteurById(id);
    }

    /**
     * Crée un nouveau visiteur à partir des informations fournies.
     *
     * @param newVisiteurParameter les informations du visiteur à créer
     * @return le visiteur créé
     */
    @PostMapping(value = "/visiteurs", consumes = "application/json;charset=UTF-8")
    Visiteur newVisiteur(@RequestBody VisiteurDTO newVisiteurParameter) {
        try{
            Visiteur visiteur = this.visiteurService.newVisiteur(newVisiteurParameter);
            return visiteur;
        }
        catch (EntityExistsException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        }
    }

    /**
     * Endpoint pour connecter un employé en utilisant son adresse e-mail et définir le rôle de la session.
     *
     * @param visiteurParameter les informations de l'employé pour la connexion
     * @param session          la session HTTP
     * @return la réponse HTTP avec un message de connexion réussie
     */
    @PostMapping("/visiteurs/connexion")
    public ResponseEntity<String> loginVisteurByMail(@RequestBody VisiteurDTO visiteurParameter, HttpSession session) {
        String mail = visiteurParameter.getMail();
        Visiteur visiteur = this.visiteurService.getVisiteurByMail(mail);
        return ResponseEntity.ok("Connexion réussie avec l'e-mail : " + mail);
    }
}
