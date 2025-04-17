package com.gestion.today.service.implementation;

import com.gestion.today.persistence.models.Amortization;
import com.gestion.today.persistence.models.Client;
import com.gestion.today.persistence.models.num.PayType;
import com.gestion.today.persistence.models.num.StateSeparationType;
import com.gestion.today.persistence.repository.RepositoryAmortization;
import com.gestion.today.persistence.repository.RepositoryClient;
import com.gestion.today.service.exceptiones.AmortizationExceededException;
import com.gestion.today.service.http.response.AmortizationResponse;
import com.gestion.today.service.interfaces.AmortizationService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
@Transactional
public class AmortizationServiceImpl implements AmortizationService {

    private final RepositoryClient repositoryClient;
    private final RepositoryAmortization repositoryAmortization;

    @Override
    public AmortizationResponse registerAmortization(String idClient, int dni, Double account, PayType payType) {

        Optional<Client> optionalClient = repositoryClient.findById(idClient);

        if (optionalClient.isEmpty() || optionalClient.get().getDni() != dni) {
            throw new AmortizationExceededException("Client not fount or DNI mismatch.");
        }
        Client client = optionalClient.get();

        Double totalAmortization = repositoryAmortization.totalAmortizedByCustomer(idClient);
        if (totalAmortization == null) totalAmortization = 0.0;

        Double totalClient = client.getTotal();
        Double newTotal = totalAmortization + account;

        if (newTotal > totalClient) {
            throw new AmortizationExceededException("Do not amortize more than the total customer already registered");
        }

        Amortization amortization = Amortization.builder()
                .idClient(client)
                .account(account)
                .pay(payType)
                .build();
        repositoryAmortization.save(amortization);

        List<Amortization> list = repositoryAmortization.findByIdClientId(idClient);
        List<AmortizationResponse.DetailAmortizationResponse> detalles = list.stream()
                .map(a -> AmortizationResponse.DetailAmortizationResponse.builder()
                        .account(a.getAccount())
                        .payType(a.getPay())
                        .date(a.getRegistrationAmortization())
                        .build())
                .toList();
        String state;
        Double remaining = totalClient - newTotal;
        if(newTotal >= totalClient) {
            client.setSeparationType(StateSeparationType.CANCELED);
            state = "CANCELED";
        }else {
            client.setSeparationType(StateSeparationType.AMORTIZANDOCE);
            state = "AMORTIZANDOCE";
        }
        repositoryClient.save(client);
        return  AmortizationResponse.builder()
                .idClient(idClient)
                .stateOperation(state)
                .totalAmortization(newTotal)
                .totalAmortize(totalClient)
                .remainingAmount(remaining)
                .details(detalles)
                .build();
    }
}
