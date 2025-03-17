package com.gestion.today.persistence.repository;

import com.gestion.today.persistence.models.Baby;
import com.gestion.today.persistence.models.Man;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RepositoryMan extends JpaRepository<Man, Integer> {

    @Query("SELECT MAX(codToday) FROM Man WHERE brand = :brand")
    Optional<String> findMaxCodTodayByBrand(@Param("brand") String brand);


    Optional<Man> findByBrandAndCodToday(String brand, String codToday);
}
