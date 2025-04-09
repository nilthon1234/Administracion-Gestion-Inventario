package com.gestion.today.utils;

import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ReporteGenerator {

    public JasperPrint generateTicketReport(List<Map<String, Object>> ticketData) throws JRException {
        JasperReport ticketReport = JasperCompileManager.compileReport(
                getClass().getResourceAsStream("/reports/Ticket.jrxml"));

        return JasperFillManager.fillReport(ticketReport,new HashMap<>(), new JRBeanCollectionDataSource(ticketData));
    }

    public JasperPrint generateDetailsReport(List<Map<String, Object>> detailsData) throws JRException {
        JasperReport detailsReport = JasperCompileManager.compileReport(
                getClass().getResourceAsStream("/reports/Details.jrxml"));

        return JasperFillManager.fillReport(detailsReport, new HashMap<>(), new JRBeanCollectionDataSource(detailsData));
    }

    public byte[] combineReports(JasperPrint ticketPrint, JasperPrint detailsPrint) throws JRException, IOException {
        List<JasperPrint> prints = Arrays.asList(ticketPrint, detailsPrint);
        JRPdfExporter exporter = new JRPdfExporter();

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        exporter.setExporterInput(SimpleExporterInput.getInstance(prints));
        exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(outputStream));
        exporter.exportReport();

        return outputStream.toByteArray();
    }

}
