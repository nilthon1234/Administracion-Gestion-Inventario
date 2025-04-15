package com.gestion.today.persistence.repository;

import com.gestion.today.persistence.models.Baby;
import com.gestion.today.persistence.models.Child;
import com.gestion.today.persistence.models.LittleGirl;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
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
    @Query("SELECT b FROM LittleGirl b WHERE b.registrationDate BETWEEN :start AND :end")
    List<LittleGirl> findByRegistrationDateBetween(@Param("start") Date start, @Param("end") Date end);
    @Query("""
    SELECT l FROM LittleGirl l
    WHERE (:brand IS NULL OR l.brand = :brand) AND (
        :size IS NULL OR
        (:size = 28 AND l.eu28 > 0) OR
        (:size = 28.5 AND l.eu28_5 > 0) OR
        (:size = 29 AND l.eu29 > 0) OR
        (:size = 29.5 AND l.eu29_5 > 0) OR
        (:size = 30 AND l.eu30 > 0) OR
        (:size = 30.5 AND l.eu30_5 > 0) OR
        (:size = 31 AND l.eu31 > 0) OR
        (:size = 31.5 AND l.eu31_5 > 0) OR
        (:size = 32 AND l.eu32 > 0) OR
        (:size = 32.5 AND l.eu32_5 > 0) OR
        (:size = 33 AND l.eu33 > 0) OR
        (:size = 33.5 AND l.eu33_5 > 0) OR
        (:size = 34 AND l.eu34 > 0) OR
        (:size = 34.5 AND l.eu34_5 > 0) OR
        (:size = 35 AND l.eu35 > 0) OR
        (:size = 35.5 AND l.eu35_5 > 0) OR
        (:size = 36 AND l.eu36 > 0) OR
        (:size = 36.5 AND l.eu36_5 > 0) OR
        (:size = 37 AND l.eu37 > 0) OR
        (:size = 37.5 AND l.eu37_5 > 0) OR
        (:size = 38 AND l.eu38 > 0) OR
        (:size = 38.5 AND l.eu38_5 > 0)
    )
""")
    List<LittleGirl> findByFilters(@Param("brand") String brand, @Param("size") Double size);
    boolean existsByCompany(String company);
}
