package com.gestion.today.persistence.repository;

import com.gestion.today.persistence.models.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RepositoryClient extends JpaRepository<Client, String> {
    @Query("select MAX(c.id) FROM Client c")
    Optional<String> findMaxId();
}
