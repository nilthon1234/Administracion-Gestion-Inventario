package com.gestion.today.presentation.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gestion.today.persistence.models.Separation;
import com.gestion.today.service.http.request.SeparationRequest;
import com.gestion.today.service.http.response.ClientSeparationResponse;
import com.gestion.today.service.interfaces.SeparationSlipperService;
import com.gestion.today.service.report.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/separation")
@RestController
@RequiredArgsConstructor
public class SeparationController {

    private final SeparationSlipperService separationSlipperService;
    private final ReportService reportService;

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

    @GetMapping("/client/{id}/pdf")
    public ResponseEntity<byte[]> generateClientPdf(@PathVariable String id) {
        try {
            ClientSeparationResponse response = separationSlipperService.getClientSeparationById(id);
            byte[] pdfBytes = reportService.generateClientReport(response);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDisposition(ContentDisposition.builder("attachment")
                    .filename("client-report-" + id + ".pdf")
                    .build());

            return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(null);
        }
    }

}
