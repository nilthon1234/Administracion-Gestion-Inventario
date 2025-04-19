package com.gestion.today.presentation.controller;

import com.gestion.today.service.report.QrReportService;
import com.gestion.today.service.report.QrReportService.QrRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/reports")
public class QrReportController {

    private final QrReportService qrReportService;

    @Autowired
    public QrReportController(QrReportService qrReportService) {
        this.qrReportService = qrReportService;
    }

    @PostMapping("/qr-codes")
    public ResponseEntity<byte[]> generateQrCodeReport(@RequestBody List<QrRequest> qrRequests) {
        try {
            byte[] reportBytes = qrReportService.generateQrCodeReport(qrRequests);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDispositionFormData("attachment", "qr_codes_report.pdf");

            return new ResponseEntity<>(reportBytes, headers, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}