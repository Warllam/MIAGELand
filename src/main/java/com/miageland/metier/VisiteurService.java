package com.miageland.metier;

import com.miageland.DAO.VisiteurRepository;
import com.miageland.DTO.VisiteurDTO;
import com.miageland.model.Visiteur;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VisiteurService {

    @Autowired
    private VisiteurRepository visiteurRepository;

    public VisiteurService(){

    }

    public Visiteur newVisiteur(VisiteurDTO newVisiteurParameter){
        Visiteur visiteur = new Visiteur(newVisiteurParameter.getNom(), newVisiteurParameter.getPrenom(), newVisiteurParameter.getMail());
        this.visiteurRepository.save(visiteur);
        return visiteur;
    }
}
