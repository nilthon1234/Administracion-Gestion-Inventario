package com.gestion.today.service.mapper;

import com.gestion.today.persistence.models.*;
import com.gestion.today.presentation.dto.SlipperDTO;

import java.util.HashMap;
import java.util.Map;

public class SlipperMapper {

    public static SlipperDTO mapToDto(Baby baby){

        SlipperDTO dto = new SlipperDTO();
        dto.setId(baby.getId());
        dto.setBrand(baby.getBrand());
        dto.setCodToday(baby.getCodToday());
        dto.setCompany(baby.getCompany());
        dto.setAmount(baby.getAmount());
        Map<String, Integer> sizes = new HashMap<>();
        sizes.put("eu18", baby.getEu18());
        sizes.put("eu18_5", baby.getEu18_5());
        sizes.put("eu19", baby.getEu19());
        sizes.put("eu19_5", baby.getEu19_5());
        sizes.put("eu20", baby.getEu20());
        sizes.put("eu20_5", baby.getEu20_5());
        sizes.put("eu21", baby.getEu21());
        sizes.put("eu21_5", baby.getEu21_5());
        sizes.put("eu22", baby.getEu22());
        sizes.put("eu22_5", baby.getEu22_5());
        sizes.put("eu23", baby.getEu23());
        sizes.put("eu23_5", baby.getEu23_5());
        sizes.put("eu24", baby.getEu24());
        sizes.put("eu24_5", baby.getEu24_5());
        sizes.put("eu25", baby.getEu25());
        sizes.put("eu25_5", baby.getEu25_5());
        sizes.put("eu26", baby.getEu26());
        sizes.put("eu26_5", baby.getEu26_5());
        sizes.put("eu27", baby.getEu27());
        sizes.put("eu27_5", baby.getEu27_5());
        dto.setSizes(sizes);

        return dto;
    }

    public static SlipperDTO mapToDto(Child child){
        SlipperDTO dto = new SlipperDTO();

        dto.setId(child.getId());
        dto.setBrand(child.getBrand());
        dto.setCodToday(child.getCodToday());
        dto.setCompany(child.getCompany());
        dto.setAmount(child.getAmount());

        Map<String, Integer> sizes = new HashMap<>();

        sizes.put("eu28", child.getEu28());
        sizes.put("eu28_5", child.getEu28_5());
        sizes.put("eu29", child.getEu29());
        sizes.put("eu29_5", child.getEu29_5());
        sizes.put("eu30", child.getEu30());
        sizes.put("eu30_5", child.getEu30_5());
        sizes.put("eu31", child.getEu31());
        sizes.put("eu31_5", child.getEu31_5());
        sizes.put("eu32", child.getEu32());
        sizes.put("eu32_5", child.getEu32_5());
        sizes.put("eu33", child.getEu33());
        sizes.put("eu33_5", child.getEu33_5());
        sizes.put("eu34", child.getEu34());
        sizes.put("eu34_5", child.getEu34_5());
        sizes.put("eu35", child.getEu35());
        sizes.put("eu35_5", child.getEu35_5());
        sizes.put("eu36", child.getEu36());
        sizes.put("eu36_5", child.getEu36_5());
        sizes.put("eu37", child.getEu37());
        sizes.put("eu37_5", child.getEu37_5());
        sizes.put("eu38", child.getEu38());
        sizes.put("eu38_5", child.getEu38_5());
        dto.setSizes(sizes);

        return  dto;

    }
    public static SlipperDTO mapToDto(LittleGirl littleGirl){
        SlipperDTO dto = new SlipperDTO();

        dto.setId(littleGirl.getId());
        dto.setBrand(littleGirl.getBrand());
        dto.setCodToday(littleGirl.getCodToday());
        dto.setCompany(littleGirl.getCompany());
        dto.setAmount(littleGirl.getAmount());

        Map<String, Integer> sizes = new HashMap<>();

        sizes.put("eu28", littleGirl.getEu28());
        sizes.put("eu28_5", littleGirl.getEu28_5());
        sizes.put("eu29", littleGirl.getEu29());
        sizes.put("eu29_5", littleGirl.getEu29_5());
        sizes.put("eu30", littleGirl.getEu30());
        sizes.put("eu30_5", littleGirl.getEu30_5());
        sizes.put("eu31", littleGirl.getEu31());
        sizes.put("eu31_5", littleGirl.getEu31_5());
        sizes.put("eu32", littleGirl.getEu32());
        sizes.put("eu32_5", littleGirl.getEu32_5());
        sizes.put("eu33", littleGirl.getEu33());
        sizes.put("eu33_5", littleGirl.getEu33_5());
        sizes.put("eu34", littleGirl.getEu34());
        sizes.put("eu34_5", littleGirl.getEu34_5());
        sizes.put("eu35", littleGirl.getEu35());
        sizes.put("eu35_5", littleGirl.getEu35_5());
        sizes.put("eu36", littleGirl.getEu36());
        sizes.put("eu36_5", littleGirl.getEu36_5());
        sizes.put("eu37", littleGirl.getEu37());
        sizes.put("eu37_5", littleGirl.getEu37_5());
        sizes.put("eu38", littleGirl.getEu38());
        sizes.put("eu38_5", littleGirl.getEu38_5());
        dto.setSizes(sizes);

        return  dto;

    }
    public static SlipperDTO mapToDto(Man man){
        SlipperDTO dto = new SlipperDTO();

        dto.setId(man.getId());
        dto.setBrand(man.getBrand());
        dto.setCodToday(man.getCodToday());
        dto.setCompany(man.getCompany());
        dto.setAmount(man.getAmount());

        Map<String, Integer> sizes = new HashMap<>();

        sizes.put("eu5_5", man.getUsa5_5());
        sizes.put("eu6", man.getUsa6());
        sizes.put("eu6_5", man.getUsa6_5());
        sizes.put("eu7", man.getUsa7());
        sizes.put("eu7_5", man.getUsa7_5());
        sizes.put("eu8", man.getUsa8());
        sizes.put("eu8_5", man.getUsa8_5());
        sizes.put("eu9", man.getUsa9());
        sizes.put("eu9_5", man.getUsa9_5());
        sizes.put("eu10", man.getUsa10());
        sizes.put("eu10_5", man.getUsa10_5());
        sizes.put("eu11", man.getUsa11());
        sizes.put("eu11_5", man.getUsa11_5());
        sizes.put("eu12", man.getUsa12());
        dto.setSizes(sizes);

        return  dto;

    }
    public static SlipperDTO mapToDto(Women women){
        SlipperDTO dto = new SlipperDTO();

        dto.setId(women.getId());
        dto.setBrand(women.getBrand());
        dto.setCodToday(women.getCodToday());
        dto.setCompany(women.getCompany());
        dto.setAmount(women.getAmount());

        Map<String, Integer> sizes = new HashMap<>();

        sizes.put("eu5_5", women.getUsa5_5());
        sizes.put("eu6", women.getUsa6());
        sizes.put("eu6_5", women.getUsa6_5());
        sizes.put("eu7", women.getUsa7());
        sizes.put("eu7_5", women.getUsa7_5());
        sizes.put("eu8", women.getUsa8());
        sizes.put("eu8_5", women.getUsa8_5());
        sizes.put("eu9", women.getUsa9());
        sizes.put("eu9_5", women.getUsa9_5());
        sizes.put("eu10", women.getUsa10());
        sizes.put("eu10_5", women.getUsa10_5());
        sizes.put("eu11", women.getUsa11());
        sizes.put("eu11_5", women.getUsa11_5());
        sizes.put("eu12", women.getUsa12());
        dto.setSizes(sizes);

        return  dto;

    }
}
