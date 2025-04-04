package com.gestion.today.persistence.repository;

import com.gestion.today.persistence.models.Baby;
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

    @Query("SELECT MAX(codToday) FROM Baby WHERE brand = :brand")
    Optional<String> findMaxCodTodayByBrand(@Param("brand") String brand);

    Optional<Baby> findByBrandAndCodToday(String brand, String codToday);
    //filter
    @Query("SELECT b FROM Baby b WHERE b.registrationDate BETWEEN :start AND :end")
    List<Baby> findByRegistrationDateBetween(@Param("start") Date start, @Param("end") Date end);
    @Query("""
    SELECT b FROM Baby b
    WHERE (:brand IS NULL OR b.brand = :brand) AND (
        :size IS NULL OR
        (:size = 18 AND b.eu18 > 0) OR
        (:size = 18.5 AND b.eu18_5 > 0) OR
        (:size = 19 AND b.eu19 > 0) OR
        (:size = 19.5 AND b.eu19_5 > 0) OR
        (:size = 20 AND b.eu20 > 0) OR
        (:size = 20.5 AND b.eu20_5 > 0) OR
        (:size = 21 AND b.eu21 > 0) OR
        (:size = 21.5 AND b.eu21_5 > 0) OR
        (:size = 22 AND b.eu22 > 0) OR
        (:size = 22.5 AND b.eu22_5 > 0) OR
        (:size = 23 AND b.eu23 > 0) OR
        (:size = 23.5 AND b.eu23_5 > 0) OR
        (:size = 24 AND b.eu24 > 0) OR
        (:size = 24.5 AND b.eu24_5 > 0) OR
        (:size = 25 AND b.eu25 > 0) OR
        (:size = 25.5 AND b.eu25_5 > 0) OR
        (:size = 26 AND b.eu26 > 0) OR
        (:size = 26.5 AND b.eu26_5 > 0) OR
        (:size = 27 AND b.eu27 > 0) OR
        (:size = 27.5 AND b.eu27_5 > 0)
    )
""")
    List<Baby> findByFilters(@Param("brand") String brand, @Param("size") Double size);

}
