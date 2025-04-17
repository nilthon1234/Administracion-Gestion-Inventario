package com.gestion.today.service.http.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.gestion.today.persistence.models.num.PayType;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AmortizationResponse {
    private String idClient;
    private String stateOperation;
    private Double totalAmortization;
    private Double totalAmortize;
    private Double remainingAmount;
    private List<DetailAmortizationResponse> details;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class DetailAmortizationResponse {
        private Double account;
        private PayType payType;

        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "America/Lima")
        private Date date;

    }
}
