package com.gestion.today.presentation.controller;

import com.gestion.today.presentation.dto.AmortizationDto;
import com.gestion.today.service.http.response.AmortizationResponse;
import com.gestion.today.service.interfaces.AmortizationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RequestMapping("/amortization")
@RequiredArgsConstructor
@RestController
public class AmortizationController {

    private final AmortizationService amortizationService;

    @PostMapping("register")
    public ResponseEntity<AmortizationResponse> registerAmortitation(@RequestBody AmortizationDto request) {
        AmortizationResponse result = amortizationService.registerAmortization(
                request.getIdClient(),
                request.getDni(),
                request.getAccount(),
                request.getPayType()
        );
        return ResponseEntity.ok(result);
    }
}
