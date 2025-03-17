package com.gestion.today.presentation.dto;

import jakarta.persistence.Column;
import lombok.Data;

import java.util.Map;

@Data
public class SlipperDTO {

    private Integer id;
    private String brand;
    private String codToday;
    private int amount;
    private String image;
    private String company;
    private Map<String, Integer> sizes;

}
