package com.miageland.metier;

import com.miageland.DAO.AttractionRepository;
import com.miageland.DTO.AttractionDTO;
import com.miageland.exception.AttractionNotFoundException;
import com.miageland.model.Attraction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Service
public class AttractionService {
    @Autowired
    private AttractionRepository attractionRepository;

    public AttractionService(){
    }

    public Attraction newAttraction(AttractionDTO newAttractionParameter){
        Attraction attraction = new Attraction(newAttractionParameter.getNom(), newAttractionParameter.isEstOuverte());
        this.attractionRepository.save(attraction);
        return attraction;
    }

    public List<Attraction> getAllAttractions() {
        return this.attractionRepository.findAll();
    }

    public List<Attraction> getAttractionsOuvertes() {
        return this.attractionRepository.findByEstOuverte(true);
    }
    public Attraction updateAttraction(Long id, boolean estOuverte) {
        Attraction attraction = attractionRepository.findById(id).orElseThrow(() -> new AttractionNotFoundException(id));
        attraction.setEstOuverte(estOuverte);
        this.attractionRepository.save(attraction);
        return attraction;
    }

    public Attraction deleteAttraction(Long id) {
        Attraction attraction = attractionRepository.findById(id).orElseThrow(() -> new AttractionNotFoundException(id));
        this.attractionRepository.delete(attraction);
        return attraction;
    }
}
