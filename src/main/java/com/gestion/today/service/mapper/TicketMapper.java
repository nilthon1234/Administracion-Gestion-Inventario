package com.gestion.today.service.mapper;

import com.gestion.today.persistence.models.DetailTicket;
import com.gestion.today.persistence.models.Ticket;
import com.gestion.today.service.http.response.TicketResponse;

import java.util.List;

public class TicketMapper {



    private static TicketResponse.DetailDTO mapDetail(DetailTicket detailTicket) {

        return TicketResponse.DetailDTO.builder()
                .amount(detailTicket.getAmount())
                .codToday(detailTicket.getCodToday())
                .size(detailTicket.getSize())
                .price(detailTicket.getPrice())
                .sub_total(detailTicket.getSubTotal())
                .build();
    }
    public static TicketResponse.TicketDTO mapTicket(Ticket ticket, List<DetailTicket> detailTicket) {
        return TicketResponse.TicketDTO.builder()
                .nro_ticket(ticket.getNroTicket())
                .sellerName(ticket.getSellerName())
                .clientName(ticket.getClientName())
                .client_last_name(ticket.getClientLastName())
                .registrationTicket(ticket.getRegistrationTicket())
                .dni(ticket.getDni())
                .detail(detailTicket.stream()
                        .map(TicketMapper::mapDetail)
                        .toList())
                .build();
    }
}
