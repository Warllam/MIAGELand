package com.miageland.DAO;

import com.miageland.model.Visiteur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VisiteurRepository extends JpaRepository<Visiteur, Long> {

}
