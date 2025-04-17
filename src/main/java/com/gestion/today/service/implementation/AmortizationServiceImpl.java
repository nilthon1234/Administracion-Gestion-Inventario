package com.gestion.today.service.implementation;

import com.gestion.today.persistence.models.Amortization;
import com.gestion.today.persistence.models.Client;
import com.gestion.today.persistence.models.num.PayType;
import com.gestion.today.persistence.models.num.StateSeparationType;
import com.gestion.today.persistence.repository.RepositoryAmortization;
import com.gestion.today.persistence.repository.RepositoryClient;
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
    public String registerAmortization(String idClient, int dni, Double account, PayType payType) {

        Optional<Client> optionalClient = repositoryClient.findById(idClient);
        if (optionalClient.isEmpty()) {
            return "Client not found";
        }
        Client client = optionalClient.get();

        //mandamos un erro si en caso el dni no coincida con el idClient
        if (client.getDni() != dni) {
            return "the dni does not match the idClient";
        }

        Double totalAmortization = repositoryAmortization.totalAmortizedByCustomer(idClient);
        if (totalAmortization == null) totalAmortization = 0.0;

        Double totalClient = client.getTotal();
        if (totalAmortization + account > totalClient) {
            return "no se puede amortizar mas.Ya alcanzo  o supero al total del Client";
        }
        Amortization amortization = Amortization.builder()
                .idClient(client)
                .account(account)
                .pay(payType)
                .build();
        repositoryAmortization.save(amortization);

        Double newTotal = totalAmortization + account;
        StringBuilder response = new StringBuilder();
        if (newTotal >= totalClient) {
            client.setSeparationType(StateSeparationType.CANCELED);
            response.append("Su operation by idClient: ")
                    .append(String.format("%s",idClient))
                    .append("ha sido cancelado: Total amortizado: s/.")
                    .append(String.format("%.2f",newTotal));
        }else {
            client.setSeparationType(StateSeparationType.AMORTIZANDOCE);
            List<Amortization> list = repositoryAmortization.findByIdClientId(idClient);
            response.append("Amortization registrada: Details:\n");

            for (Amortization a : list) {
                response.append("- Monto: s/.")
                        .append(String.format("%.2f", a.getAccount()))
                        .append(" | Metodo de pago: ").append(a.getPay())
                        .append("| Fecha: ").append(a.getRegistrationAmortization()).append("\n");
            }
            Double restante = totalClient - newTotal;
            response.append("Monto Restante para completar el pago: s/.")
                    .append(String.format("%.2f", restante));
        }
        repositoryClient.save(client);
        return response.toString();
    }
}
