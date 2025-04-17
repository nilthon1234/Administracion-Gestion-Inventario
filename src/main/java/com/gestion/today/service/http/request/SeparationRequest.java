package com.gestion.today.service.http.request;

import com.gestion.today.persistence.models.Client;
import com.gestion.today.persistence.models.num.PayType;
import lombok.*;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SeparationRequest {

    private Client client;
    private List<separationDto> detailsSeparation;
    private List<amortizationDto> detailsAmortization;

    @Getter
    @Setter
    public static class separationDto {
        private int idClient;
        private String codToday;
        private List<String> size;
        private Double amount;
        private Double price;
        private Double subTotal;
    }

    @Getter
    @Setter
    public static class amortizationDto{
        private int idClient;
        private int dni;
        private Double account;
        private String pay;
    }
}
