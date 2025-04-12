package com.gestion.today.service.report;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRSaver;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.InputStream;

@Component
public class ReportCompiler {

    public JasperReport compileTicketReport() throws JRException {
        InputStream reportStream = ReportCompiler.class.getResourceAsStream("/reports/TicketReport.jrxml");
        return JasperCompileManager.compileReport(reportStream);
    }
    public JasperReport compileDetailsSubReport() throws JRException {
        InputStream reportStream = ReportCompiler.class.getResourceAsStream("/reports/DetailsReport.jrxml");
        JasperReport subReport = JasperCompileManager.compileReport(reportStream);
        saveSubReportTemp(subReport);
        return subReport;
    }

    private void saveSubReportTemp(JasperReport subReport) throws JRException {
        String tempDir = getTempDir();
        String subReportPath = tempDir + "DetailsReport.jasper";
        JRSaver.saveObject(subReport, subReportPath);
    }

    private String getTempDir(){
        String tempDir = System.getProperty("java.io.tmpdir");
        return tempDir.endsWith(File.separator)?tempDir:tempDir+File.separator;
    }

}
