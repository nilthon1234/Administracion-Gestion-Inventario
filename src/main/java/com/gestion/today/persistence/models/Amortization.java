package com.gestion.today.persistence.models;

import com.gestion.today.persistence.models.num.PayType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Amortization {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne
    @JoinColumn(name = "id_client",referencedColumnName = "id")
    private Client idClient;
    private Double account;

    @Enumerated(EnumType.STRING)
    private PayType pay;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "registration_Amortization", updatable=false)
    private Date registrationAmortization;

    @PrePersist
    protected void onCreate(){
        registrationAmortization = new Date();
    }


}
