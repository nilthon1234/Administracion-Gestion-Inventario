package com.gestion.today.presentation.controller;

import com.gestion.today.persistence.models.SlipperType;
import com.gestion.today.presentation.dto.SlipperDTO;
import com.gestion.today.service.interfaces.FilterSlipperService;
import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.DateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@RequestMapping("/filter-slipper")
@RestController
@AllArgsConstructor
public class FilterSlipperController {

    private final FilterSlipperService filterSlipperService;

    //identificamos las nuevas zapatillas que fueron registradas por ese dia
    @GetMapping("/date-slipper")
    public ResponseEntity<List<SlipperDTO>> filterAllSlipper(@RequestParam("fecha")
                                                             @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                                                             LocalDate date){
        return ResponseEntity.ok(filterSlipperService.filterDate(date));

    }

    @GetMapping("/{tableType}")
    public ResponseEntity<List<SlipperDTO>> filterSlipper(@PathVariable SlipperType tableType,
                                                          @RequestParam(required = false) String brand,
                                                          @RequestParam(required = false)Double size){
        List<SlipperDTO> result = filterSlipperService.filterSlipper(tableType, brand, size);
        return ResponseEntity.ok(result);
    }

}
