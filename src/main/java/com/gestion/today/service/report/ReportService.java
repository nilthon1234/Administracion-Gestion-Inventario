package com.gestion.today.service.report;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gestion.today.service.http.response.ClientSeparationResponse;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JsonDataSource;
import net.sf.jasperreports.engine.query.JsonQueryExecuterFactory;
import net.sf.jasperreports.engine.util.JRLoader;
import org.apache.tomcat.util.http.fileupload.ByteArrayOutputStream;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

@Service
public class ReportService {

    public byte[] generateClientReport(ClientSeparationResponse response) throws Exception {
        // 1. Convertir el objeto Java a JSON
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(response);

        // 2. Crear el JsonDataSource con la ruta client[0]
        ByteArrayInputStream jsonDataStream = new ByteArrayInputStream(json.getBytes(StandardCharsets.UTF_8));
        JsonDataSource dataSource = new JsonDataSource(jsonDataStream, "client");

        // 3. Preparar parámetros para Jasper
        Map<String, Object> params = new HashMap<>();

        // 4. Crear carpeta temporal para los subreportes y copiarlos desde el classpath
        File subreportDirFile = new File(System.getProperty("java.io.tmpdir"), "subreports");
        if (!subreportDirFile.exists()) {
            subreportDirFile.mkdirs();
        }

        // Lista de subreportes necesarios
        String[] subreports = { "SeparationReport.jasper", "AmortizationReport.jasper" };
        for (String subreport : subreports) {
            InputStream is = new ClassPathResource("Report-GrupoToday/" + subreport).getInputStream();
            File targetFile = new File(subreportDirFile, subreport);
            try (OutputStream os = new FileOutputStream(targetFile)) {
                is.transferTo(os);
            }
        }

        // 5. Establecer ruta del subreporte como parámetro
        params.put("SUBREPORT_DIR", subreportDirFile.getAbsolutePath() + File.separator);

        // 6. Cargar y compilar el reporte principal desde el jrxml
        JasperReport jasperReport = JasperCompileManager.compileReport(
                new ClassPathResource("Report-GrupoToday/ClientReport.jrxml").getInputStream()
        );

        // 7. Llenar el reporte con datos
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, params, dataSource);

        // 8. Exportar a PDF
        return JasperExportManager.exportReportToPdf(jasperPrint);
    }
}
