package com.gestion.today.service.interfaces;

import com.gestion.today.persistence.models.SlipperType;
import com.gestion.today.presentation.dto.SlipperDTO;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public interface FilterSlipperService {
    List<SlipperDTO> filterDate (LocalDate date);
    List<SlipperDTO> filterSlipper (SlipperType slipperType, String brand, Double size);
}
