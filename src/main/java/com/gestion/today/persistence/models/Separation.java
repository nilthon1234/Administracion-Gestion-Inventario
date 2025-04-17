package com.gestion.today.persistence.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Separation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;


    @ManyToOne
    @Enumerated(EnumType.STRING)
    @JoinColumn(name = "id_client", referencedColumnName = "id")
    private Client idClient;

    private String codToday;
    private String size;
    private int amount;
    private Double price;
    private Double subTotal;
}
