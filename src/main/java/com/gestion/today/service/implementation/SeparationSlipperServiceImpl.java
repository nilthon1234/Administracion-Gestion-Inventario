package com.gestion.today.service.implementation;

import com.gestion.today.persistence.models.Amortization;
import com.gestion.today.persistence.models.Client;
import com.gestion.today.persistence.models.Separation;
import com.gestion.today.persistence.models.num.PayType;
import com.gestion.today.persistence.models.num.StateSeparationType;
import com.gestion.today.persistence.repository.RepositoryAmortization;
import com.gestion.today.persistence.repository.RepositoryClient;
import com.gestion.today.persistence.repository.RepositorySeparation;
import com.gestion.today.service.http.request.SeparationRequest;
import com.gestion.today.service.http.response.ClientSeparationResponse;
import com.gestion.today.service.http.response.TicketResponse;
import com.gestion.today.service.interfaces.SeparationSlipperService;
import com.gestion.today.service.mapper.ClientSeparationMapper;
import com.gestion.today.utils.GenerateNroIdClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Transactional()
@Service
@RequiredArgsConstructor
public class SeparationSlipperServiceImpl implements SeparationSlipperService {

    private  final RepositorySeparation repositorySeparation;
    private  final RepositoryClient repositoryClient;
    private final RepositoryAmortization repositoryAmortization;
    private final GenerateNroIdClient generateNroIdClient;

    @Override
    public void   save(SeparationRequest request) throws Exception {
        String nextIdClient = generateNroIdClient.generateNexNroIdClient();
        Client client = Client.builder()
                .id(nextIdClient)
                .name(request.getClient().getName())
                .lastName(request.getClient().getLastName())
                .dni(request.getClient().getDni())
                .total(0.0)
                .separationType(StateSeparationType.NUEVO)
                .build();
        repositoryClient.save(client);
        double total = 0.0;

        for (SeparationRequest.separationDto dto : request.getDetailsSeparation()) {
            int amount = dto.getSize().size();
            double subtotal = amount * dto.getPrice();

            Separation separation = Separation.builder()
                    .idClient(client)
                    .codToday(dto.getCodToday())
                    .amount(amount)
                    .price(dto.getPrice())
                    .subTotal(subtotal)
                    .size(dto.getSize().stream()
                            .map(String::valueOf)
                            .collect(Collectors.joining(",")))
                    .build();
            repositorySeparation.save(separation);
            total += separation.getSubTotal();
        }
        client.setTotal(total);
        repositoryClient.save(client);

        for (SeparationRequest.amortizationDto dto : request.getDetailsAmortization()) {
            Amortization amortization = Amortization.builder()
                    .idClient(client)
                    .account(dto.getAccount())
                    .pay(PayType.valueOf(dto.getPay()))
                    .build();
            repositoryAmortization.save(amortization);
        }
    }

    @Override
    public ClientSeparationResponse getClientSeparationById(String id) {
        Client client = repositoryClient.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));
        List<Separation> separation = repositorySeparation.findByIdClient_Id(id);
        List<Amortization> amortization = repositoryAmortization.findByIdClientId(id);

        return ClientSeparationResponse.builder()
                .client(List.of(ClientSeparationMapper.mapClient(client,separation,amortization)))
                .build();
    }
}
