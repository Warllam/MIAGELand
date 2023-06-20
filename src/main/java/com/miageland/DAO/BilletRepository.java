package com.miageland.DAO;

import com.miageland.model.Billet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface BilletRepository extends JpaRepository<Billet, Long> {
    List<Billet> findByVisiteurId(Long idVisiteur);

    List<Billet> findByDateValidite(LocalDate dateValidite);
}
