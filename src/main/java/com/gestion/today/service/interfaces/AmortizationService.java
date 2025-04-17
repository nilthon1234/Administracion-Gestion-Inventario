package com.gestion.today.service.interfaces;

import com.gestion.today.persistence.models.num.PayType;

public interface AmortizationService {

    String registerAmortization(String idClient, int dni, Double amount, PayType payType);
}
