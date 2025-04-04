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
}
