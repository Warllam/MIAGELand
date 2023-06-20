package com.miageland.exposition;

import com.miageland.DAO.AttractionRepository;
import com.miageland.DTO.AttractionDTO;
import com.miageland.exception.AttractionNotFoundException;
import com.miageland.metier.AttractionService;
import com.miageland.model.Attraction;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Contrôleur REST pour gérer les attractions.
 */
@RestController
@RequestMapping("/attractions")
public class AttractionController {

    private final AttractionService attractionService;

    public AttractionController(AttractionService attractionService) {
        this.attractionService = attractionService;
    }

    /**
     * Endpoint pour récupérer la liste de toutes les attractions.
     *
     * @return la réponse HTTP avec la liste de toutes les attractions
     */
    @GetMapping
    public ResponseEntity<List<Attraction>> getAllAttractions() {
        return ResponseEntity.ok(this.attractionService.getAllAttractions());
    }

    /**
     * Endpoint pour créer une nouvelle attraction.
     *
     * @param newAttractionParameter les informations de la nouvelle attraction
     * @return la réponse HTTP avec la nouvelle attraction créée
     */
    @PostMapping(consumes = "application/json;charset=UTF-8")
    public ResponseEntity<Attraction> createNewAttraction(@RequestBody AttractionDTO newAttractionParameter) {
        Attraction attraction = attractionService.newAttraction(newAttractionParameter);
        return ResponseEntity.status(HttpStatus.CREATED).body(attraction);
    }

    /**
     * Endpoint pour mettre à jour l'état d'ouverture d'une attraction spécifiée par son identifiant.
     *
     * @param id          l'identifiant de l'attraction à mettre à jour
     * @param estOuverte  l'état d'ouverture de l'attraction
     * @return la réponse HTTP avec l'attraction mise à jour
     */
    @PutMapping("/{id}/setOuverture")
    public ResponseEntity<Attraction> updateAttractionOuverture(@PathVariable Long id, @RequestParam("estOuverte") boolean estOuverte) {
        try {
            return ResponseEntity.ok(this.attractionService.updateAttraction(id, estOuverte));
        } catch (AttractionNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Endpoint pour récupérer la liste des attractions ouvertes.
     *
     * @return la réponse HTTP avec la liste des attractions ouvertes
     */
    @GetMapping("/ouvertes")
    public ResponseEntity<List<Attraction>> getAttractionsOuvertes() {
        return ResponseEntity.ok(this.attractionService.getAttractionsOuvertes());
    }

    /**
     * Endpoint pour supprimer une attraction spécifiée par son identifiant.
     *
     * @param id l'identifiant de l'attraction à supprimer
     * @return la réponse HTTP avec le statut OK si l'attraction a été supprimée avec succès, sinon le statut NotFound
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Attraction> deleteAttraction(@PathVariable Long id) {
        try {
            attractionService.deleteAttraction(id);
            return ResponseEntity.ok().build();
        } catch (AttractionNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

}
