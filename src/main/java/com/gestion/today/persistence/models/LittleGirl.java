package com.gestion.today.persistence.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table
public class LittleGirl {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String brand;
    @Column(name = "cod_today")
    private String codToday;
    private int amount;
    private String image;

    private int eu28;
    private int eu28_5;

    private int eu29;
    private int eu29_5;

    private int eu30;
    private int eu30_5;

    private int eu31;
    private int eu31_5;

    private int eu32;
    private int eu32_5;

    private int eu33;
    private int eu33_5;

    private int eu34;
    private int eu34_5;

    private int eu35;
    private int eu35_5;

    private int eu36;
    private int eu36_5;

    private int eu37;
    private int eu37_5;

    private int eu38;
    private int eu38_5;
}
