package com.gestion.today.service.interfaces;

import com.gestion.today.persistence.models.SlipperType;
import com.gestion.today.presentation.dto.SlipperDTO;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

public interface CodTodayService {
    String saveCodToday(String tableName, String brand, String company, MultipartFile file)throws IOException;

    Optional<SlipperDTO> detailsShoe(SlipperType slipperType, String brand, String codToday);
    String updateQR(String codToday, String tableName, String brand, String company);
}
