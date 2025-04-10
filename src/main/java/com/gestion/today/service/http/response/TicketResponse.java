package com.gestion.today.service.http.response;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Builder
@Data
public class TicketResponse {

   private  List<TicketDTO> ticket;

    @Getter
    @Setter
    @Builder
    public  static class  DetailDTO{
        private Double sub_total;
        private Double price;
    }

    @Getter
    @Setter
    @Builder
    public  static  class TicketDTO{
        private Integer nro_ticket;
        private String client_last_name;
        private int dni;
        private List<DetailDTO> detail;
    }

}
