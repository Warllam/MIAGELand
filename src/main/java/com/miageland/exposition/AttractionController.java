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

@RestController
@RequestMapping("/attractions")
public class AttractionController {

    private final AttractionService attractionService;

    public AttractionController(AttractionRepository attractionRepository, AttractionService attractionService) {
        this.attractionService = attractionService;
    }

    @GetMapping
    public ResponseEntity<List<Attraction>> getAllAttractions() {
        return ResponseEntity.ok(this.attractionService.getAllAttractions());
    }

    @PostMapping(consumes = "application/json;charset=UTF-8")
    public ResponseEntity<Attraction> createNewAttraction(@RequestBody AttractionDTO newAttractionParameter) {
        Attraction attraction = attractionService.newAttraction(newAttractionParameter);
        return ResponseEntity.status(HttpStatus.CREATED).body(attraction);
    }

    @PutMapping("/{id}/setOuverture")
    public ResponseEntity<Attraction> updateAttractionOuverture(@PathVariable Long id, @RequestParam("estOuverte") boolean estOuverte) {
        try{
            return ResponseEntity.ok(this.attractionService.updateAttraction(id,estOuverte));
         } catch (AttractionNotFoundException e) {
            return ResponseEntity.notFound().build();
         }
    }

    @GetMapping("/ouvertes")
    public ResponseEntity<List<Attraction>> getAttractionsOuvertes() {
        return ResponseEntity.ok(this.attractionService.getAttractionsOuvertes());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Attraction> deleteAttraction(@PathVariable Long id) {
        try{
            attractionService.deleteAttraction(id);
            return ResponseEntity.ok().build();
        } catch (AttractionNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

}
