package com.gestion.today.presentation.controller;

import com.gestion.today.persistence.models.Separation;
import com.gestion.today.service.http.request.SeparationRequest;
import com.gestion.today.service.http.response.ClientSeparationResponse;
import com.gestion.today.service.interfaces.SeparationSlipperService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/separation")
@RestController
@RequiredArgsConstructor
public class SeparationController {

    private final SeparationSlipperService separationSlipperService;

    @PostMapping("/save")
    public ResponseEntity<String> saveSeparation(@RequestBody SeparationRequest request){
        try {
            separationSlipperService.save(request);
            return ResponseEntity.ok().body("Separation saved");

        } catch (Exception e) {
            return ResponseEntity.status(500).body("ERROR AL GUARDAR SEPARATION: " + e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClientSeparationResponse> getClientSeparationList(@PathVariable("id") String id){
        try {
            ClientSeparationResponse response = separationSlipperService.getClientSeparationById(id);
            return ResponseEntity.ok(response);
        }catch (RuntimeException e){
            return ResponseEntity.notFound().build();
        }
    }

}
