package com.gestion.today.service.interfaces;

import com.gestion.today.persistence.models.num.PayType;
import com.gestion.today.service.http.response.AmortizationResponse;

public interface AmortizationService {

    AmortizationResponse registerAmortization(String idClient, int dni, Double amount, PayType payType);
}
