package com.gestion.today.service.interfaces;

import com.gestion.today.service.http.request.TicketRequest;
import com.gestion.today.service.http.response.TicketResponse;
import net.sf.jasperreports.engine.JRException;

import java.io.IOException;

public interface SaleTicketService {

   void   registerTicketAndDetailsSave (TicketRequest request);
   TicketResponse getTicketByNro(Integer nroTicket);
   byte[] generatePDF(String ticketId)throws JRException, IOException;
}
