package com.gestion.today.presentation.controller;

import com.gestion.today.service.http.request.TicketRequest;
import com.gestion.today.service.interfaces.SaleTicketService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/sale")
public class SaleController {

    private final SaleTicketService saleTicketService;
    @PostMapping("/registerSale")
    public ResponseEntity<String> registerTicket(@RequestBody TicketRequest request){
        try {
            saleTicketService.registerTicketAndDetailsSave(request);
            return ResponseEntity.ok("successful registration of ticket and detailsTicket.");
        }catch (RuntimeException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("ERROR: " + e.getMessage());
        }
    }
}
