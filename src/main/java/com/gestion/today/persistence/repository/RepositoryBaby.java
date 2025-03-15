package com.gestion.today.persistence.repository;

import com.gestion.today.persistence.models.Baby;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface RepositoryBaby extends JpaRepository<Baby, Integer> {
    Optional<Baby> findByCodToday(String codToday);

    @Transactional
    @Query("SELECT b.eu18 + b.eu18_5 + b.eu19 + b.eu19_5 + " +
            "b.eu20 + b.eu20_5 + b.eu21 + b.eu21_5 + " +
            "b.eu22 + b.eu22_5 + b.eu23 + b.eu23_5 + " +
            "b.eu24 + b.eu24_5 + b.eu25 + b.eu25_5 + " +
            "b.eu26 + b.eu26_5 + b.eu27 + b.eu27_5 " +
            "FROM Baby b WHERE b.codToday = :codToday")
    int calculateTotalAmount(String codToday);
}
