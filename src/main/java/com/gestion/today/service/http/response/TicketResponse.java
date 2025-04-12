package com.gestion.today.service.http.response;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Builder
@Data
public class TicketResponse {

   private  List<TicketDTO> ticket;

    @Getter
    @Setter
    @Builder
    public  static class  DetailDTO{
        private  int amount;
        private String codToday;
        private String size;
        private Double price;
        private Double sub_total;
        //requerrido por jaspert studio
        private BigDecimal totalPagar;
    }

    @Getter
    @Setter
    @Builder
    public  static  class TicketDTO{
        private Integer nro_ticket;
        private String sellerName;
        private String clientName;
        private String client_last_name;
        private Date registrationTicket;
        private int dni;
        private List<DetailDTO> detail;
    }

}
