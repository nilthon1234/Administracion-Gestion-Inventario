package com.gestion.today.service.implementation;

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
}
