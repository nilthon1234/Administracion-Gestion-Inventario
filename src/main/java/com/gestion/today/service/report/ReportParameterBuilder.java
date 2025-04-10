package com.gestion.today.service.report;

import com.gestion.today.service.http.response.TicketResponse;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Component
public class ReportParameterBuilder {

    public Map<String,Object> buildReportParameter(TicketResponse ticketResponse, JasperReport subReport) {
        Map<String,Object> map = new HashMap<>();
        String tempDir = getTempDir();
        map.put("SUBREPORT_DIR",tempDir);

        if (!ticketResponse.getTicket().isEmpty()) {
            List<TicketResponse.DetailDTO> details = ticketResponse.getTicket().get(0).getDetail();
            JRBeanCollectionDataSource detailsDS = new JRBeanCollectionDataSource(details);
            map.put("detailsDataSource", detailsDS);
        }
        return map;
    }

    private String getTempDir(){
        String tempDir = System.getProperty("java.io.tmpdir");
        return tempDir.endsWith(File.separator)?tempDir:tempDir+File.separator;
    }
}
