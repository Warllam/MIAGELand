package com.miageland.metier;

import com.miageland.DAO.VisiteurRepository;
import com.miageland.DTO.VisiteurDTO;
import com.miageland.exception.VisiteurNotFoundException;
import com.miageland.model.Employe;
import com.miageland.model.Visiteur;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**

 Service de gestion des visiteurs.
 */
@Service
public class VisiteurService {

    @Autowired
    private VisiteurRepository visiteurRepository;

    /**

     Récupère tous les visiteurs.
     @return la liste de tous les visiteurs
     */
    public List<Visiteur> getAllVisiteurs() {
        return this.visiteurRepository.findAll();
    }
    /**

     Récupère un visiteur en fonction de son identifiant.
     @param id l'identifiant du visiteur
     @return le visiteur correspondant
     @throws VisiteurNotFoundException si le visiteur n'est pas trouvé
     */
    public Visiteur getVisiteurById(Long id) {
        return this.visiteurRepository.getReferenceById(id);
    }
    /**

     Supprime un visiteur en fonction de son identifiant.
     @param id l'identifiant du visiteur à supprimer
     @throws VisiteurNotFoundException si le visiteur n'est pas trouvé
     */
    public void deleteVisiteurById(Long id) {
        if (visiteurRepository.existsById(id)) {
            visiteurRepository.deleteById(id);
        } else {
            throw new VisiteurNotFoundException(id);
        }
    }
    /**

     Crée un nouveau visiteur en fonction des paramètres spécifiés.
     @param newVisiteurParameter les paramètres du nouveau visiteur
     @return le visiteur créé
     */
    public Visiteur newVisiteur(VisiteurDTO newVisiteurParameter) {
        Visiteur visiteur = new Visiteur(newVisiteurParameter.getNom(), newVisiteurParameter.getPrenom(), newVisiteurParameter.getMail());
        this.visiteurRepository.save(visiteur);
        return visiteur;
    }

    public boolean existVisiteurByEmail(String mail) {
        Optional<Visiteur> visiteur = visiteurRepository.findByMail(mail);
        return visiteur.isPresent();
    }

    public Visiteur getVisiteurByMail(String mail) {
        if(existVisiteurByEmail(mail)) {
            return visiteurRepository.findByMail(mail).get();
        }else{
            throw new VisiteurNotFoundException();
        }
    }

}