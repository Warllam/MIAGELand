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

    private final AttractionRepository attractionRepository;
    private final AttractionService attractionService;

    public AttractionController(AttractionRepository attractionRepository, AttractionService attractionService) {
        this.attractionRepository = attractionRepository;
        this.attractionService = attractionService;
    }

    @GetMapping
    public ResponseEntity<List<Attraction>> getAllAttractions() {
        List<Attraction> attractions = attractionRepository.findAll();
        return ResponseEntity.ok(attractions);
    }

    @PostMapping(consumes = "application/json;charset=UTF-8")
    public ResponseEntity<Attraction> createNewAttraction(@RequestBody AttractionDTO newAttractionParameter) {
        Attraction attraction = attractionService.newAttraction(newAttractionParameter);
        return ResponseEntity.status(HttpStatus.CREATED).body(attraction);
    }

    @PutMapping("/{id}/ouverture")
    public ResponseEntity<Attraction> updateAttractionOuverture(@PathVariable Long id, @RequestBody boolean estOuverte) {
        Attraction attraction = attractionRepository.findById(id)
                .orElseThrow(() -> new AttractionNotFoundException(id));

        Attraction updatedAttraction = attractionService.setAttractionOuverture(attraction, estOuverte);
        return ResponseEntity.ok(updatedAttraction);
    }

    @GetMapping("/ouvertes")
    public ResponseEntity<List<Attraction>> getAttractionsOuvertes() {
        List<Attraction> attractionsOuvertes = attractionRepository.findByEstOuverte(true);
        return ResponseEntity.ok(attractionsOuvertes);
    }

}
