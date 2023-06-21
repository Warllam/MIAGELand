package com.miageland.metier;

import com.miageland.DAO.AttractionRepository;
import com.miageland.DTO.AttractionDTO;
import com.miageland.exception.AttractionNotFoundException;
import com.miageland.model.Attraction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * Service métier pour gérer les attractions.
 */
@Service
public class AttractionService {

    @Autowired
    private AttractionRepository attractionRepository;

    /**
     * Crée une nouvelle attraction à partir des paramètres spécifiés.
     *
     * @param newAttractionParameter les informations de la nouvelle attraction
     * @return l'attraction créée
     */
    public Attraction newAttraction(AttractionDTO newAttractionParameter) {

        Attraction attraction = new Attraction(newAttractionParameter.getNom(), newAttractionParameter.isEstOuverte());
        this.attractionRepository.save(attraction);
        return attraction;
    }

    /**
     * Récupère la liste de toutes les attractions.
     *
     * @return la liste de toutes les attractions
     */
    public List<Attraction> getAllAttractions() {
        return this.attractionRepository.findAll();
    }

    /**
     * Récupère la liste des attractions ouvertes.
     *
     * @return la liste des attractions ouvertes
     */
    public List<Attraction> getAttractionsOuvertes() {
        return this.attractionRepository.findByEstOuverte(true);
    }

    /**
     * Met à jour l'état d'ouverture d'une attraction spécifiée par son identifiant.
     *
     * @param id          l'identifiant de l'attraction à mettre à jour
     * @param estOuverte  le nouvel état d'ouverture de l'attraction
     * @return l'attraction mise à jour
     * @throws AttractionNotFoundException si l'attraction n'est pas trouvée
     */
    public Attraction updateAttraction(Long id, boolean estOuverte) throws AttractionNotFoundException {


        Attraction attraction = attractionRepository.findById(id).orElseThrow(() -> new AttractionNotFoundException(id));
        attraction.setEstOuverte(estOuverte);
        this.attractionRepository.save(attraction);
        return attraction;
    }

    /**
     * Supprime une attraction spécifiée par son identifiant.
     *
     * @param id l'identifiant de l'attraction à supprimer
     * @return l'attraction supprimée
     * @throws AttractionNotFoundException si l'attraction n'est pas trouvée
     */
    public Attraction deleteAttraction(Long id) throws AttractionNotFoundException {

        Attraction attraction = attractionRepository.findById(id).orElseThrow(() -> new AttractionNotFoundException(id));
        this.attractionRepository.delete(attraction);
        return attraction;
    }


}
