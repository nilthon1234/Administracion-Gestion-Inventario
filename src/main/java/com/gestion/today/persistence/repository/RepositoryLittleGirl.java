package com.gestion.today.persistence.repository;

import com.gestion.today.persistence.models.Baby;
import com.gestion.today.persistence.models.LittleGirl;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface RepositoryLittleGirl extends JpaRepository<LittleGirl, Integer> {

    Optional<LittleGirl> findByCodToday(String codToday);
    @Transactional
    @Query("SELECT l.eu28 + l.eu28_5 + l.eu29 + l.eu29_5 + " +
            "l.eu30 + l.eu30_5 + l.eu31 + l.eu31_5 + l.eu32 + l.eu32_5 + " +
            "l.eu33 + l.eu33_5 + l.eu34 + l.eu34_5 + l.eu35 + l.eu35_5 + " +
            "l.eu36 + l.eu36_5 + l.eu37 + l.eu37_5 + l.eu38 + l.eu38_5 " +
            "FROM LittleGirl l WHERE l.codToday = :codToday")
    int calculateTotalAmount(String codToday);

    @Query("SELECT MAX(codToday) FROM LittleGirl WHERE brand = :brand")
    Optional<String> findMaxCodTodayByBrand(@Param("brand") String brand);

    Optional<LittleGirl> findByBrandAndCodToday(String brand, String codToday);
}
