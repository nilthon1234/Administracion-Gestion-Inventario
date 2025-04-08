package com.gestion.today.persistence.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "tb_ticket")
public class Ticket {

    @Id
    private Integer nroTicket;
    private String sellerName;
    private String clientName;
    private String clientLastName;
    private int dni;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "registration_Ticket", updatable = false)
    private Date registrationTicket;

    @PrePersist
    protected void  onCreate(){ registrationTicket = new Date();}
}
