package com.gestion.today.persistence.repository;

import com.gestion.today.persistence.models.Separation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RepositorySeparation extends JpaRepository<Separation, Integer> {
}
