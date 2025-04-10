package com.gestion.today.presentation.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gestion.today.persistence.repository.RepositoryTicket;
import com.gestion.today.service.http.request.TicketRequest;
import com.gestion.today.service.http.response.TicketResponse;
import com.gestion.today.service.interfaces.SaleTicketService;
import com.gestion.today.utils.GenerateTicketReport;
import com.gestion.today.utils.ReporteGenerator;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.data.JsonDataSource;
import net.sf.jasperreports.engine.util.JRSaver;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
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


    //REPORT
    public byte[] generateReport(Integer nroTicket) throws JRException {
        // Obtener datos
        TicketResponse ticketResponse = saleTicketService.getTicketByNro(nroTicket);

        // Cargar y compilar reportes
        InputStream reportStream = getClass().getResourceAsStream("/reports/VentaReport.jrxml");
        InputStream subReportStream = getClass().getResourceAsStream("/reports/DetailsSub.jrxml");
        JasperReport mainReport = JasperCompileManager.compileReport(reportStream);
        JasperReport subReport = JasperCompileManager.compileReport(subReportStream);

        // Guardar el subreporte compilado en ubicación temporal
        String tempDir = System.getProperty("java.io.tmpdir");
        if (!tempDir.endsWith(File.separator)) {
            tempDir += File.separator;
        }
        String subReportPath = tempDir + "DetailsSub.jasper";
        JRSaver.saveObject(subReport, subReportPath);

        // Establecer parámetros
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("SUBREPORT_DIR", tempDir); // Asegurarse de que no sea null
        System.out.println("SUBREPORT_DIR: " + tempDir); // Para depuración

        // Crear data source para el subreporte
        if (!ticketResponse.getTicket().isEmpty()) {
            // Usar directamente la lista de DetailDTO
            List<TicketResponse.DetailDTO> details = ticketResponse.getTicket().get(0).getDetail();
            JRBeanCollectionDataSource detailsDataSource = new JRBeanCollectionDataSource(details);
            parameters.put("detailsDataSource", detailsDataSource);
        }

        // Llenar reporte
        JRBeanCollectionDataSource mainDataSource = new JRBeanCollectionDataSource(ticketResponse.getTicket());
        JasperPrint jasperPrint = JasperFillManager.fillReport(mainReport, parameters, mainDataSource);

        return JasperExportManager.exportReportToPdf(jasperPrint);
    }

    @GetMapping("/sale/{nroTicket}/report")
    public ResponseEntity<byte[]> exportReport(@PathVariable Integer nroTicket) {
        try {
            byte[] pdfReport = generateReport(nroTicket);

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
