package com.gestion.today.service.mapper;

import com.gestion.today.persistence.models.DetailTicket;
import com.gestion.today.persistence.models.Ticket;
import com.gestion.today.service.http.response.TicketResponse;

import java.util.List;
import java.util.stream.Collectors;

public class TicketMapper {



    private static TicketResponse.DetailDTO mapDetail(DetailTicket detailTicket) {

        return TicketResponse.DetailDTO.builder()
                .sub_total(detailTicket.getSubTotal())
                .price(detailTicket.getPrice())
                .build();
    }
    public static TicketResponse.TicketDTO mapTicket(Ticket ticket, List<DetailTicket> detailTicket) {
        return TicketResponse.TicketDTO.builder()
                .nro_ticket(ticket.getNroTicket())
                .client_last_name(ticket.getClientLastName())
                .dni(ticket.getDni())
                .detail(detailTicket.stream()
                        .map(TicketMapper::mapDetail)
                        .toList())
                .build();
    }
}
