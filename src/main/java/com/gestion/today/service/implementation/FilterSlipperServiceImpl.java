package com.gestion.today.service.implementation;

import com.gestion.today.persistence.models.*;
import com.gestion.today.persistence.models.num.SlipperType;
import com.gestion.today.persistence.repository.*;
import com.gestion.today.presentation.dto.SlipperDTO;
import com.gestion.today.service.interfaces.FilterSlipperService;
import com.gestion.today.service.mapper.SlipperMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RequiredArgsConstructor
@Service
public class FilterSlipperServiceImpl implements FilterSlipperService {

    private final RepositoryMan repositoryMan;
    private final RepositoryBaby repositoryBaby;
    private final RepositoryWomen repositoryWomen;
    private final RepositoryChild repositoryChild;
    private final RepositoryLittleGirl repositoryLittleGirl;


    @Override
    public List<SlipperDTO> filterDate(LocalDate date) {

        List<SlipperDTO> result = new ArrayList<>();
        //ajustando al inio del dia 00:00:00
        Date startOfDay = Timestamp.valueOf(date.atStartOfDay());
        Date endOfDay = Timestamp.valueOf(date.plusDays(1).atStartOfDay());

        repositoryBaby.findByRegistrationDateBetween(startOfDay,endOfDay)
                .forEach(baby -> result.add(SlipperMapper.mapToDto(baby)));
        repositoryChild.findByRegistrationDateBetween(startOfDay, endOfDay)
                .forEach(child -> result.add(SlipperMapper.mapToDto(child)));
        repositoryMan.findByRegistrationDateBetween(startOfDay, endOfDay)
                .forEach(man -> result.add(SlipperMapper.mapToDto(man)));
        repositoryWomen.findByRegistrationDateBetween(startOfDay, endOfDay)
                .forEach(women -> result.add(SlipperMapper.mapToDto(women)));
        repositoryLittleGirl.findByRegistrationDateBetween(startOfDay, endOfDay)
                .forEach(littleGirl -> result.add(SlipperMapper.mapToDto(littleGirl)));

        return result;
    }

    @Override
    public List<SlipperDTO> filterSlipper(SlipperType slipperType, String brand, Double size) {

        List<SlipperDTO> result = new ArrayList<>();

        switch (slipperType){
            case BABY:
                repositoryBaby.findByFilters(brand, size)
                        .forEach(entity -> result.add(SlipperMapper.mapToDto((Baby) entity)));
                break;
            case CHILD:
                repositoryChild.findByFilters(brand, size)
                        .forEach(entity -> result.add(SlipperMapper.mapToDto((Child) entity)));
                break;
            case LITTLEGIRL:
                repositoryLittleGirl.findByFilters(brand, size)
                        .forEach(entity -> result.add(SlipperMapper.mapToDto((LittleGirl) entity)));
                break;
            case MAN:
                repositoryMan.findByFilters(brand, size)
                        .forEach(entity -> result.add(SlipperMapper.mapToDto((Man) entity)));
                break;
            case WOMEN:
                repositoryWomen.findByFilters(brand, size)
                        .forEach(entity -> result.add(SlipperMapper.mapToDto((Women) entity)));
                break;
            default:
                throw new IllegalArgumentException("Tipo de Slipper no valido: " + slipperType);

        }

        return  result;
    }
}
