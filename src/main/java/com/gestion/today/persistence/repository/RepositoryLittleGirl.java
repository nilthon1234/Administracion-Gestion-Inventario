package com.gestion.today.persistence.repository;

import com.gestion.today.persistence.models.Baby;
import com.gestion.today.persistence.models.LittleGirl;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RepositoryLittleGirl extends JpaRepository<LittleGirl, Integer> {

    @Query("SELECT MAX(codToday) FROM LittleGirl WHERE brand = :brand")
    Optional<String> findMaxCodTodayByBrand(@Param("brand") String brand);

    Optional<LittleGirl> findByBrandAndCodToday(String brand, String codToday);
}
