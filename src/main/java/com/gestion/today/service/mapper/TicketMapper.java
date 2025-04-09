package com.gestion.today.service.mapper;

import com.gestion.today.persistence.models.DetailTicket;
import com.gestion.today.persistence.models.Ticket;
import com.gestion.today.service.http.response.TicketResponse;

import java.util.List;
import java.util.stream.Collectors;

public class TicketMapper {

    public static TicketResponse toDto(List<Ticket> ticket, List<DetailTicket> details){
        return TicketResponse.builder()
                .ticket(ticket.stream().map(TicketMapper::mapTicket).collect(Collectors.toList()))
                .details(details.stream().map(TicketMapper::mapDetail).collect(Collectors.toList()))
                .build();
    }

    private static TicketResponse.DetailDTO mapDetail(DetailTicket detailTicket) {

        return TicketResponse.DetailDTO.builder()
                .sub_total(detailTicket.getSubTotal())
                .price(detailTicket.getPrice())
                .build();
    }
    private static TicketResponse.TicketDTO mapTicket(Ticket ticket){
        return TicketResponse.TicketDTO.builder()
                .nro_ticket(ticket.getNroTicket())
                .client_last_name(ticket.getClientLastName())
                .dni(ticket.getDni())
                .build();
    }
}
