package com.gestion.today.service.http.request;

import com.gestion.today.persistence.models.Ticket;
import lombok.*;

import java.util.List;
@AllArgsConstructor
@NoArgsConstructor
@Data
public class TicketRequest {

    private Ticket ticket;
    private List<DetailTicketRequest> details;

    @Getter
    @Setter
    public static class DetailTicketRequest{
        private double price;
        private String codToday;
        private int amount;
        private List<Double> sizes;
    }

}
