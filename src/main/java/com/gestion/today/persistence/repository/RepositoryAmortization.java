package com.gestion.today.persistence.repository;

import com.gestion.today.persistence.models.Amortization;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RepositoryAmortization extends JpaRepository<Amortization, Integer> {
    @Query("SELECT SUM(a.account) FROM Amortization a WHERE a.idClient.id = :idClient")
    Double totalAmortizedByCustomer(@Param("idClient")String idClient);

    List<Amortization> findByIdClientId(String idClient);
}
