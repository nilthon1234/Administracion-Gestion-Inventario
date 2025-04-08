package com.gestion.today.utils;

import com.gestion.today.persistence.models.Ticket;
import com.gestion.today.persistence.repository.RepositoryTicket;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class GenerateNroTicket {

    private final RepositoryTicket repositoryTicket;

    public Integer generateNextNroTicket(){
        Optional<Integer> maxNroTicket = repositoryTicket.findMaxNroTicket();
        return maxNroTicket.map(n -> n + 1).orElse(1000001);
    }
}
