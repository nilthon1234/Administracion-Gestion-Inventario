package com.gestion.today.service.interfaces;

import com.gestion.today.persistence.models.SlipperType;
import com.gestion.today.presentation.dto.SlipperDTO;

import java.util.Optional;

public interface CodTodayService {
    String saveCodToday(String tableName, String brand, String company);

    Optional<SlipperDTO> detailsShoe(SlipperType slipperType, String brand, String codToday);
}
