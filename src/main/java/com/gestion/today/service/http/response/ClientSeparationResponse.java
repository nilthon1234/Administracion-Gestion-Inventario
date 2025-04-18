package com.gestion.today.service.http.response;

import com.gestion.today.persistence.models.Client;
import com.gestion.today.persistence.models.num.PayType;
import com.gestion.today.persistence.models.num.StateSeparationType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ClientSeparationResponse {
    private List<ClientDto> client;

    @Data
    @Builder
    public static class ClientDto{
        private String id;
        private String name;
        private String lastName;
        private int dni;
        private Double total;

        @Enumerated(EnumType.STRING)
        private StateSeparationType separationType;
        private List<SeparationDto> separations;
        private List<AmortizationDto> amortizations;
    }

    @Data
    @Builder
    public static class SeparationDto{

        private String idClient;
        private String codToday;
        private String size;
        private int amount;
        private Double price;
        private Double subTotal;
    }

    @Data
    @Builder
    public static class AmortizationDto{
        private String idClient;
        private Double account;

        @Enumerated(EnumType.STRING)
        private PayType pay;

        @Temporal(TemporalType.TIMESTAMP)
        private Date registrationAmortization;
    }


}
