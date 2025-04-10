package com.gestion.today.service.report;

import com.gestion.today.service.http.response.TicketResponse;
import com.gestion.today.service.interfaces.SaleTicketService;
import lombok.RequiredArgsConstructor;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class ReportGeneratorTicket {

    private final SaleTicketService saleTicketService;
    private final ReportCompiler reportCompiler;
    private final ReportParameterBuilder reportParameterBuilder;


    public byte[] generateReport(Integer nroTicket)throws JRException{
        TicketResponse response = saleTicketService.getTicketByNro(nroTicket);

        JasperReport ticketRepert = reportCompiler.compileTicketReport();
        JasperReport detailsSubReport = reportCompiler.compileDetailsSubReport();

        Map<String, Object> params = reportParameterBuilder.buildReportParameter(response, detailsSubReport);

        JRBeanCollectionDataSource mainDataSource = new JRBeanCollectionDataSource(response.getTicket());
        JasperPrint jasperPrint = JasperFillManager.fillReport(ticketRepert, params, mainDataSource);
        return JasperExportManager.exportReportToPdf(jasperPrint);
    }
}
