package com.gestion.today.persistence.repository;

import com.gestion.today.persistence.models.Separation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RepositorySeparation extends JpaRepository<Separation, Integer> {
    List<Separation> findByIdClient_Id(String idClientId);
}
