package com.gestion.today.service.interfaces;

import com.gestion.today.service.http.request.TicketRequest;

public interface SaleTicketService {

   void   registerTicketAndDetailsSave (TicketRequest request);
}
