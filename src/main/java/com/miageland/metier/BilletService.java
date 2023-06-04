package com.miageland.metier;

import com.miageland.DAO.BilletRepository;
import com.miageland.DAO.VisiteurRepository;
import com.miageland.DTO.BilletDTO;
import com.miageland.exception.BilletException;
import com.miageland.model.Billet;
import com.miageland.model.Visiteur;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;

@Service
public class BilletService {

    @Autowired
    private BilletRepository billetRepository;

    @Autowired
    private VisiteurRepository visiteurRepository;
    public BilletService(){}

    public Billet newBillet(BilletDTO newBilletParameter){
        Visiteur visiteur = visiteurRepository.getReferenceById(newBilletParameter.getIdVisiteur());
        if(visiteur == null){
            throw new BilletException("Visiteur non trouvé");
        }
        Billet billet = new Billet(newBilletParameter.getDateValidite(), newBilletParameter.getPrix(), newBilletParameter.getDateVente(), visiteur);
        this.billetRepository.save(billet);
        return billet;
    }

    //supprimer un billet
    public String deleteBillet(Long idBillet){
        Billet billet = billetRepository.findById(idBillet).orElseThrow(() -> new BilletException("Could not find billet " + idBillet));

        // Récupérer la date actuelle et enleve 7 jours
        Date dateActuelle = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dateActuelle);
        calendar.add(Calendar.DAY_OF_MONTH, -7);
        Date dateMoins7Jours = calendar.getTime();

        if (dateMoins7Jours.after(billet.getDateValidite())){
            throw new IllegalStateException("Impossible de supprimer un billet à moins de 7 jours de la date de Validité");
        }else{
            String message = "Billet remboursé : "+billet.getPrix();
            this.billetRepository.deleteById(idBillet);
            return message;
        }
    }


}
