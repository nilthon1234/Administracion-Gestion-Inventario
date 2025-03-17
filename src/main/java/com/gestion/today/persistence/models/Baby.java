package com.gestion.today.persistence.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table
public class Baby {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String brand;
    @Column(name = "cod_today")
    private String codToday;
    private int amount;
    private String image;
    @Column(name = "cod_Company")
    private String company;

    private int eu18;
    private int eu18_5;

    private int eu19;
    private int eu19_5;

    private int eu20;
    private int eu20_5;

    private int eu21;
    private int eu21_5;

    private int eu22;
    private int eu22_5;

    private int eu23;
    private int eu23_5;

    private int eu24;
    private int eu24_5;

    private int eu25;
    private int eu25_5;

    private int eu26;
    private int eu26_5;

    private int eu27;
    private int eu27_5;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "registration_date", updatable = false)
    private Date registrationDate;

    @PrePersist
    protected void onCreate(){
        registrationDate = new Date();
    }

}
