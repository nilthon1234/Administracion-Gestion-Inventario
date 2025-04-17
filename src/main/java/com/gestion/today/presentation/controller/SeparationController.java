package com.gestion.today.presentation.controller;

import com.gestion.today.service.http.request.SeparationRequest;
import com.gestion.today.service.interfaces.SeparationSlipperService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
