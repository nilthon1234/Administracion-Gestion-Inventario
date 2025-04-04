package com.gestion.today.presentation.controller;

import com.gestion.today.presentation.dto.SlipperDTO;
import com.gestion.today.service.interfaces.FilterSlipperService;
import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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

}
