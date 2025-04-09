package com.gestion.today.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Service
public class GenerateTicketReport {


    @Autowired
    private RestTemplate restTemplate;

    public List<Map<String, Object>> getTicketData(String ticketId) {
        String jsonUrl = "http://localhost:8080/sale/" + ticketId;
        Map<String, Object> response = restTemplate.getForObject(jsonUrl, Map.class);

        return (List<Map<String, Object>>) response.get("ticket");
    }


    public List<Map<String, Object>> getDetailsData(String ticketId) {
        String jsonUrl = "http://localhost:8080/sale/" + ticketId;
        Map<String, Object> response = restTemplate.getForObject(jsonUrl, Map.class);

        return (List<Map<String, Object>>) response.get("details");
    }
}
