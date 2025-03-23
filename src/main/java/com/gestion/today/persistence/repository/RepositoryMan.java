package com.gestion.today.persistence.repository;

import com.gestion.today.persistence.models.Baby;
import com.gestion.today.persistence.models.Man;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

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
}
