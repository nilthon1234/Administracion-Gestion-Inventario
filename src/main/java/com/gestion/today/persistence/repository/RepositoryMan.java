package com.gestion.today.persistence.repository;

import com.gestion.today.persistence.models.Baby;
import com.gestion.today.persistence.models.Man;
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
public interface RepositoryMan extends JpaRepository<Man, Integer> {


    Optional<Man> findByCodToday(String codToday);
    @Transactional
    @Query("SELECT m.usa5_5 + m.usa6 + m.usa6_5 + m.usa7 + m.usa7_5 + m.usa8 + m.usa8_5 + " +
            "m.usa9 + m.usa9_5 + m.usa10 + m.usa10_5 + m.usa11 + m.usa11_5 + m.usa12 " +
            " FROM Man m WHERE m.codToday = :codToday")
    int calculateTotalAmount(String codToday);

    @Query("SELECT MAX(codToday) FROM Man WHERE brand = :brand")
    Optional<String> findMaxCodTodayByBrand(@Param("brand") String brand);


    Optional<Man> findByBrandAndCodToday(String brand, String codToday);
    @Query("SELECT b FROM Man b WHERE b.registrationDate BETWEEN :start AND :end")
    List<Man> findByRegistrationDateBetween(@Param("start") Date start, @Param("end") Date end);
    @Query("""
    SELECT m FROM Man m
    WHERE (:brand IS NULL OR m.brand = :brand) AND (
        :size IS NULL OR
        (:size = 5.5 AND m.usa5_5 > 0) OR
        (:size = 6 AND m.usa6 > 0) OR
        (:size = 6.5 AND m.usa6_5 > 0) OR
        (:size = 7 AND m.usa7 > 0) OR
        (:size = 7.5 AND m.usa7_5 > 0) OR
        (:size = 8 AND m.usa8 > 0) OR
        (:size = 8.5 AND m.usa8_5 > 0) OR
        (:size = 9 AND m.usa9 > 0) OR
        (:size = 9.5 AND m.usa9_5 > 0) OR
        (:size = 10 AND m.usa10 > 0) OR
        (:size = 10.5 AND m.usa10_5 > 0) OR
        (:size = 11 AND m.usa11 > 0) OR
        (:size = 11.5 AND m.usa11_5 > 0) OR
        (:size = 12 AND m.usa12 > 0)
    )
""")
    List<Man> findByFilters(@Param("brand") String brand, @Param("size") Double size);
    boolean existsByCompany(String company);
}
