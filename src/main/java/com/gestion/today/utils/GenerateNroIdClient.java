package com.gestion.today.utils;

import com.gestion.today.persistence.repository.RepositoryClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class GenerateNroIdClient {

    private final RepositoryClient repositoryClient;
    public String generateNexNroIdClient() {
        Optional<String> maxIdClient = repositoryClient.findMaxId();
        return maxIdClient.map(
                id ->{
                int numeroNroId = Integer.parseInt(id);
                int nextId = numeroNroId + 1;
                return String.format("%08d", nextId);
                }).orElse("00000001");
    }
}
