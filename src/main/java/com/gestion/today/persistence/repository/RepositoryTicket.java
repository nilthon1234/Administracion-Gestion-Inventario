package com.gestion.today.persistence.repository;

import com.gestion.today.persistence.models.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RepositoryTicket extends JpaRepository<Ticket, Integer> {
    @Query("SELECT MAX(t.nroTicket) FROM Ticket t")
    Optional<Integer> findMaxNroTicket();
    List<Ticket> findByNroTicket(Integer nroTicket);
}
