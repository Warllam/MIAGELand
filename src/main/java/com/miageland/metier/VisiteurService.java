package com.miageland.metier;

import com.miageland.DAO.VisiteurRepository;
import com.miageland.DTO.VisiteurDTO;
import com.miageland.exception.VisiteurNotFoundException;
import com.miageland.model.Visiteur;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VisiteurService {

    @Autowired
    private VisiteurRepository visiteurRepository;

    public VisiteurService(){

    }

    public List<Visiteur> getAllVisiteurs(){
        return this.visiteurRepository.findAll();
    }

    public Visiteur getVisiteurById(Long id){
        return this.visiteurRepository.getReferenceById(id);
    }

    public void deleteVisiteurById(Long id){
        if (visiteurRepository.existsById(id)) {
            visiteurRepository.deleteById(id);
        } else {
            throw new VisiteurNotFoundException(id);
        }
    }

    public Visiteur newVisiteur(VisiteurDTO newVisiteurParameter){
        Visiteur visiteur = new Visiteur(newVisiteurParameter.getNom(), newVisiteurParameter.getPrenom(), newVisiteurParameter.getMail());
        this.visiteurRepository.save(visiteur);
        return visiteur;
    }
}
