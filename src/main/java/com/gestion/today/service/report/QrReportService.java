package com.gestion.today.service.report;

import com.gestion.today.utils.GenerateQrCodeService;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

@Service
public class QrReportService {

    private static final String REPORTS_PATH = "Report-GrupoToday/";
    private static final String QR_REPORT = "QrCodesReport.jrxml";

    private final GenerateQrCodeService qrCodeService;

    @Autowired
    public QrReportService(GenerateQrCodeService qrCodeService) {
        this.qrCodeService = qrCodeService;
    }

    /**
     * Genera un reporte PDF con múltiples códigos QR
     *
     * @param qrRequests Lista de solicitudes de códigos QR
     * @return Arreglo de bytes que representa el PDF generado
     * @throws Exception Si ocurre algún error durante la generación del reporte
     */
    public byte[] generateQrCodeReport(List<QrRequest> qrRequests) throws Exception {
        // Preparar el conjunto de datos para el reporte
        List<QrCodeData> qrDataList = prepareQrCodeData(qrRequests);

        // Crear el origen de datos para Jasper
        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(qrDataList);

        // Parámetros del reporte
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("ReportTitle", "Códigos QR Generados");
        parameters.put("GenerationDate", new Date());

        // Cargar y compilar el reporte
        JasperReport jasperReport = JasperCompileManager.compileReport(
                loadResource(REPORTS_PATH + QR_REPORT));

        // Llenar el reporte con datos
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);

        // Exportar a PDF
        return JasperExportManager.exportReportToPdf(jasperPrint);
    }

    /**
     * Prepara los datos de códigos QR para el reporte
     */
    private List<QrCodeData> prepareQrCodeData(List<QrRequest> qrRequests) throws Exception {
        List<QrCodeData> qrDataList = new ArrayList<>();

        for (QrRequest request : qrRequests) {
            // Para cada código, duplicarlo según la cantidad solicitada
            for (int i = 0; i < request.getQuantity(); i++) {
                // Genera o recupera el QR
                String qrPath = qrCodeService.generateQRCode(
                        request.getTableName(),
                        request.getBrand(),
                        request.getCodToday(),
                        request.getCompany());

                // Verificar que el código QR se generó correctamente
                if (!qrPath.startsWith("Error")) {
                    // Leer la imagen como bytes
                    byte[] imageData = Files.readAllBytes(Paths.get(qrPath.replace("QR save in: ", "")));

                    // Crear el objeto de datos para el reporte
                    QrCodeData qrData = new QrCodeData();
                    qrData.setTableName(request.getTableName());
                    qrData.setBrand(request.getBrand());
                    qrData.setCodToday(request.getCodToday());
                    qrData.setCompany(request.getCompany());
                    qrData.setQrImageData(imageData);

                    qrDataList.add(qrData);
                }
            }
        }

        return qrDataList;
    }

    /**
     * Carga un recurso desde el classpath
     */
    private InputStream loadResource(String path) throws IOException {
        return new ClassPathResource(path).getInputStream();
    }

    /**
     * Clase para representar una solicitud de código QR
     */
    public static class QrRequest {
        private String tableName;
        private String brand;
        private String codToday;
        private String company;
        private int quantity;

        // Getters y setters
        public String getTableName() { return tableName; }
        public void setTableName(String tableName) { this.tableName = tableName; }

        public String getBrand() { return brand; }
        public void setBrand(String brand) { this.brand = brand; }

        public String getCodToday() { return codToday; }
        public void setCodToday(String codToday) { this.codToday = codToday; }

        public String getCompany() { return company; }
        public void setCompany(String company) { this.company = company; }

        public int getQuantity() { return quantity; }
        public void setQuantity(int quantity) { this.quantity = quantity; }
    }

    /**
     * Clase para los datos del código QR que se mostrarán en el reporte
     */
    public static class QrCodeData {
        private String tableName;
        private String brand;
        private String codToday;
        private String company;
        private byte[] qrImageData;

        // Getters y setters
        public String getTableName() { return tableName; }
        public void setTableName(String tableName) { this.tableName = tableName; }

        public String getBrand() { return brand; }
        public void setBrand(String brand) { this.brand = brand; }

        public String getCodToday() { return codToday; }
        public void setCodToday(String codToday) { this.codToday = codToday; }

        public String getCompany() { return company; }
        public void setCompany(String company) { this.company = company; }

        public byte[] getQrImageData() { return qrImageData; }
        public void setQrImageData(byte[] qrImageData) { this.qrImageData = qrImageData; }
    }
}