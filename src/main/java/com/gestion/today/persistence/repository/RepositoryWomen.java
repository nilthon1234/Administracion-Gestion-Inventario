package com.gestion.today.persistence.repository;

import com.gestion.today.persistence.models.Baby;
import com.gestion.today.persistence.models.Women;
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
public interface RepositoryWomen extends JpaRepository<Women, Integer> {

    Optional<Women> findByCodToday(String codToday);
    //take into account in spaces in JPQL
    @Transactional
    @Query("SELECT w.usa5_5 + w.usa6 + w.usa6_5 + w.usa7 + w.usa7_5 + w.usa8 + w.usa8_5 +" +
            "w.usa9 + w.usa9_5 + w.usa10 + w.usa10_5 + w.usa11 + w.usa11_5 + w.usa12 " +
            "FROM Women w WHERE w.codToday = :codToday")
    int calculateTotalAmount(String codToday);

    @Query("SELECT MAX(codToday) FROM Women WHERE brand = :brand")
    Optional<String> findMaxCodTodayByBrand(@Param("brand") String brand);

    Optional<Women> findByBrandAndCodToday(String brand, String codToday);
    @Query("SELECT b FROM Women b WHERE b.registrationDate BETWEEN :start AND :end")
    List<Women> findByRegistrationDateBetween(@Param("start") Date start, @Param("end") Date end);
}
