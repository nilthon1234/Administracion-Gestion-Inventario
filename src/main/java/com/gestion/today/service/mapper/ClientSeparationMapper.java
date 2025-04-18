package com.gestion.today.service.mapper;

import com.gestion.today.persistence.models.Amortization;
import com.gestion.today.persistence.models.Client;
import com.gestion.today.persistence.models.Separation;
import com.gestion.today.service.http.response.ClientSeparationResponse;

import java.util.List;

public class ClientSeparationMapper {

    public static ClientSeparationResponse.ClientDto mapClient(Client client,
                                                                List<Separation>separation,
                                                                List<Amortization> amortization){
        return ClientSeparationResponse.ClientDto.builder()
                .id(client.getId())
                .name(client.getName())
                .lastName(client.getLastName())
                .dni(client.getDni())
                .total(client.getTotal())
                .separationType(client.getSeparationType())
                .separations(separation.stream()
                        .map(ClientSeparationMapper::mapSeparation)
                        .toList())
                .amortizations(amortization.stream()
                        .map(ClientSeparationMapper::mapAmortization)
                        .toList())
                .build();

    }

    private static ClientSeparationResponse.SeparationDto mapSeparation(Separation separation){


        return ClientSeparationResponse.SeparationDto.builder()
                .idClient(separation.getIdClient().getId())
                .codToday(separation.getCodToday())
                .amount(separation.getAmount())
                .price(separation.getPrice())
                .size(separation.getSize())
                .subTotal(separation.getSubTotal())
                .build();
    }

    private static ClientSeparationResponse.AmortizationDto mapAmortization(Amortization amortization){
        return ClientSeparationResponse.AmortizationDto.builder()
                .idClient(amortization.getIdClient().getId())
                .account(amortization.getAccount())
                .pay(amortization.getPay())
                .registrationAmortization(amortization.getRegistrationAmortization())
                .build();
    }


}
