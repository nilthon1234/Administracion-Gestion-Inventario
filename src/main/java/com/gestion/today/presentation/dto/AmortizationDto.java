package com.gestion.today.presentation.dto;

import com.gestion.today.persistence.models.num.PayType;
import lombok.Data;

@Data
public class AmortizationDto {
    private String idClient;
    private int dni;
    private Double account;
    private PayType payType;

}
