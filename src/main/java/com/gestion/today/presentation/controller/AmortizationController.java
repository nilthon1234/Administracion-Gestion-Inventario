package com.gestion.today.presentation.controller;

import com.gestion.today.presentation.dto.AmortizationDto;
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
    public ResponseEntity<Map<String, Object>> registerAmortitation(@RequestBody AmortizationDto request) {
        String result = amortizationService.registerAmortization(
                request.getIdClient(),
                request.getDni(),
                request.getAccount(),
                request.getPayType()
        );
        Map<String, Object> response = new HashMap<>();
        response.put("message", result);
        return ResponseEntity.ok(response);
    }
}
