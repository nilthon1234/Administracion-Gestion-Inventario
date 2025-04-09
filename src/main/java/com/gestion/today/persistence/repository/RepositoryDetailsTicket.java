package com.gestion.today.persistence.repository;

import com.gestion.today.persistence.models.DetailTicket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RepositoryDetailsTicket extends JpaRepository<DetailTicket, Integer> {
    List<DetailTicket> findByTicket_NroTicket(Integer nroTicket);
}
