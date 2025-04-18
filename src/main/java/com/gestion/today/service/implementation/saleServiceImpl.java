package com.gestion.today.service.implementation;

import com.gestion.today.persistence.models.DetailTicket;
import com.gestion.today.persistence.models.Ticket;
import com.gestion.today.persistence.repository.RepositoryDetailsTicket;
import com.gestion.today.persistence.repository.RepositoryTicket;
import com.gestion.today.service.http.request.TicketRequest;
import com.gestion.today.service.http.response.TicketResponse;
import com.gestion.today.service.interfaces.SaleTicketService;
import com.gestion.today.service.mapper.TicketMapper;
import com.gestion.today.utils.GenerateNroTicket;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class saleServiceImpl implements SaleTicketService {

    private final RepositoryTicket repositoryTicket;
    private final RepositoryDetailsTicket repositoryDetailsTicket;
    private final GenerateNroTicket generateNroTicket;
    private final SlipperSizeServiceImpl slipperSizeService;

    @Override
    @Transactional
    public void registerTicketAndDetailsSave(TicketRequest request) {


        Integer codGenerate = generateNroTicket.generateNextNroTicket();
        //save Ticket
        Ticket ticket = Ticket.builder()
                .nroTicket(codGenerate)
                .sellerName(request.getTicket().getSellerName())
                .clientName(request.getTicket().getClientName())
                .clientLastName(request.getTicket().getClientLastName())
                .dni(request.getTicket().getDni())
                .build();
        repositoryTicket.save(ticket);
        //process for  every detail
        for (TicketRequest.DetailTicketRequest d : request.getDetails()){

            Map<Double, Long> sizeCounts = d.getSizes().stream()
                    .collect(Collectors.groupingBy(s -> s, Collectors.counting()));

            for (Map.Entry<Double, Long> entry : sizeCounts.entrySet()) {
                Double size = entry.getKey();
                int count = entry.getValue().intValue();

                boolean update = slipperSizeService.discountStockBySize(d.getCodToday(), size, count);
                if (!update){
                    throw new RuntimeException("No hay suficiente stock para talla " + size + " del codToday: " + d.getCodToday());
                }
            }
            //register Details
            DetailTicket details = DetailTicket.builder()
                    .ticket(ticket)
                    .price(d.getPrice())
                    .amount(d.getSizes().size())
                    .subTotal(d.getPrice() * d.getSizes().size())
                    .codToday(d.getCodToday())
                    .size(d.getSizes().stream()
                            .map(String::valueOf)
                            .collect(Collectors.joining(",")))
                    .build();
            repositoryDetailsTicket.save(details);

        }
    }

    @Override
    public TicketResponse getTicketByNro(Integer nroTicket) {
        Ticket ticket = repositoryTicket.findById(nroTicket)
                .orElseThrow(() -> new RuntimeException("Number Ticket not found"));
        List<DetailTicket> detail = repositoryDetailsTicket.findByTicket_NroTicket(nroTicket);


        return TicketResponse.builder()
                .ticket(List.of(TicketMapper.mapTicket(ticket, detail)))
                .build();
    }

}
