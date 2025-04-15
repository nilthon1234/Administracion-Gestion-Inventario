package com.gestion.today.persistence.repository;

import com.gestion.today.persistence.models.Baby;
import com.gestion.today.persistence.models.Child;
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
public interface RepositoryChild extends JpaRepository<Child, Integer> {

    Optional<Child> findByCodToday(String codToday);
    @Transactional
    @Query("SELECT c.eu28 + c.eu28_5 + c.eu29 + c.eu29_5 + " +
    "c.eu30 + c.eu30_5 + c.eu31 + c.eu31_5 + c.eu32 + c.eu32_5 + " +
    "c.eu33 + c.eu33_5 + c.eu34 + c.eu34_5 + c.eu35 + c.eu35_5 + " +
    "c.eu36 + c.eu36_5 + c.eu37 + c.eu37_5 + c.eu38 + c.eu38_5 " +
    "FROM Child c WHERE c.codToday = :codToday")
    int calculateTotalAmount(String codToday);
    @Query("SELECT MAX(codToday) FROM Child WHERE brand = :brand")
    Optional<String> findMaxCodTodayByBrand(@Param("brand") String brand);

    Optional<Child> findByBrandAndCodToday(String brand, String codToday);
    @Query("SELECT b FROM Child b WHERE b.registrationDate BETWEEN :start AND :end")
    List<Child> findByRegistrationDateBetween(@Param("start") Date start, @Param("end") Date end);
    @Query("""
    SELECT c FROM Child c
    WHERE (:brand IS NULL OR c.brand = :brand) AND (
        :size IS NULL OR
        (:size = 28 AND c.eu28 > 0) OR
        (:size = 28.5 AND c.eu28_5 > 0) OR
        (:size = 29 AND c.eu29 > 0) OR
        (:size = 29.5 AND c.eu29_5 > 0) OR
        (:size = 30 AND c.eu30 > 0) OR
        (:size = 30.5 AND c.eu30_5 > 0) OR
        (:size = 31 AND c.eu31 > 0) OR
        (:size = 31.5 AND c.eu31_5 > 0) OR
        (:size = 32 AND c.eu32 > 0) OR
        (:size = 32.5 AND c.eu32_5 > 0) OR
        (:size = 33 AND c.eu33 > 0) OR
        (:size = 33.5 AND c.eu33_5 > 0) OR
        (:size = 34 AND c.eu34 > 0) OR
        (:size = 34.5 AND c.eu34_5 > 0) OR
        (:size = 35 AND c.eu35 > 0) OR
        (:size = 35.5 AND c.eu35_5 > 0) OR
        (:size = 36 AND c.eu36 > 0) OR
        (:size = 36.5 AND c.eu36_5 > 0) OR
        (:size = 37 AND c.eu37 > 0) OR
        (:size = 37.5 AND c.eu37_5 > 0) OR
        (:size = 38 AND c.eu38 > 0) OR
        (:size = 38.5 AND c.eu38_5 > 0)
    )
""")
    List<Child> findByFilters(@Param("brand") String brand, @Param("size") Double size);
    boolean existsByCompany(String company);

}
