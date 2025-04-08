package com.gestion.today.persistence.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tb_detailTicket")
public class DetailTicket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne
    @JoinColumn(name = "nro_Ticket", referencedColumnName = "nroTicket")
    private Ticket ticket;
    private Double subTotal;
    private Double price;
    private int amount;
    private String codToday;
    private String size;

}
