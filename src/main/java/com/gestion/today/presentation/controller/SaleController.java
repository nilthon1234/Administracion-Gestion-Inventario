package com.gestion.today.presentation.controller;

import com.gestion.today.service.http.request.TicketRequest;
import com.gestion.today.service.http.response.TicketResponse;
import com.gestion.today.service.interfaces.SaleTicketService;
import com.gestion.today.utils.GenerateTicketReport;
import com.gestion.today.utils.ReporteGenerator;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperPrint;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/sale")
public class SaleController {

    private final SaleTicketService saleTicketService;
    private final ReporteGenerator reporteGenerator;
    private final GenerateTicketReport generateTicketReport;

    @PostMapping("/registerSale")
    public ResponseEntity<String> registerTicket(@RequestBody TicketRequest request){
        try {
            saleTicketService.registerTicketAndDetailsSave(request);
            return ResponseEntity.ok("successful registration of ticket and detailsTicket.");
        }catch (RuntimeException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("ERROR: " + e.getMessage());
        }
    }

    @GetMapping("/{nroTicket}")
    public ResponseEntity<TicketResponse> getTicket (@PathVariable Integer nroTicket){
        try {
            TicketResponse response = saleTicketService.getTicketByNro(nroTicket);
            return ResponseEntity.ok(response);
        }catch (RuntimeException e){
            return ResponseEntity.notFound().build();
        }
    }

    //REPORTE

    @GetMapping("/export/pdf/{ticketId}")
    public ResponseEntity<byte[]> generatePdfReport(@PathVariable String ticketId)
            throws JRException, IOException {


        byte[] pdfBytes = saleTicketService.generatePDF(ticketId);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDisposition(ContentDisposition.builder("inline")
                .filename("reporte_completo.pdf").build());

        return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);
    }


}
