package com.gestion.today.presentation.controller;

import com.gestion.today.service.http.request.TicketRequest;
import com.gestion.today.service.http.response.TicketResponse;
import com.gestion.today.service.interfaces.SaleTicketService;
import com.gestion.today.service.report.ReportGeneratorTicket;
import lombok.RequiredArgsConstructor;
import net.sf.jasperreports.engine.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/sale")
public class SaleController {

    private final SaleTicketService saleTicketService;
    private final ReportGeneratorTicket reportGeneratorTicket;

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

    //REPORT

    @GetMapping("/{nroTicket}/report")
    public ResponseEntity<byte[]> exportReport(@PathVariable Integer nroTicket) {
        try {
            byte[] pdfReport = reportGeneratorTicket.generateReport(nroTicket);

            // Configurar la respuesta HTTP con el PDF generado
            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_PDF)
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=ticket_report.pdf")
                    .body(pdfReport);
        } catch (JRException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }


}
