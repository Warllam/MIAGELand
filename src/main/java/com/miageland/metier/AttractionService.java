package com.miageland.metier;

import com.miageland.DAO.AttractionRepository;
import com.miageland.DTO.AttractionDTO;
import com.miageland.model.Attraction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public Attraction setAttractionOuverture(Attraction attraction, Boolean estOuverte) {
        attraction.setEstOuverte(estOuverte);
        this.attractionRepository.save(attraction);
        return attraction;
    }
}
