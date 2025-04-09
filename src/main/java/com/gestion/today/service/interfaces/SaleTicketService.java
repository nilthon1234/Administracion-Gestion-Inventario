package com.gestion.today.service.interfaces;

import com.gestion.today.service.http.request.TicketRequest;
import com.gestion.today.service.http.response.TicketResponse;

public interface SaleTicketService {

   void   registerTicketAndDetailsSave (TicketRequest request);
   TicketResponse getTicketByNro(Integer nroTicket);
}
