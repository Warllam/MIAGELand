package com.miageland.DAO;

import com.miageland.model.Employe;
import com.miageland.model.Visiteur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VisiteurRepository extends JpaRepository<Visiteur, Long> {
    Optional<Visiteur> findByMail(String email);
    Visiteur getVisiteurByMail(String email);

}
