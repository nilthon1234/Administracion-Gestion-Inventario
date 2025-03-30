package com.gestion.today.persistence.models;

import com.gestion.today.service.interfaces.HasImage;
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
public class Man implements HasImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String brand;
    @Column(name = "cod_today")
    private String codToday;
    private int amount;
    private String image;

    @Column(name = "cod_Company", unique = true)
    private String company;

    private int usa5_5;
    private int usa6;
    private int usa6_5;
    private int usa7;
    private int usa7_5;
    private int usa8;
    private int usa8_5;
    private int usa9;
    private int usa9_5;
    private int usa10;
    private int usa10_5;
    private int usa11;
    private int usa11_5;
    private int usa12;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "registration_date", updatable = false)
    private Date registrationDate;

    @PrePersist
    protected void onCreate(){
        registrationDate = new Date();
    }
}
