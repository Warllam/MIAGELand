package com.miageland.DAO;

import com.miageland.model.Employe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmployeRepository extends JpaRepository<Employe, Integer> {
    Optional<Employe> findByMail(String email);
}
