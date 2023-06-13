package com.miageland.DAO;

import com.miageland.model.JaugeParc;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public interface JaugeParcRepository extends JpaRepository<JaugeParc, Date> {
}
