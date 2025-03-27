package com.gestion.today.presentation.controller;

import com.gestion.today.persistence.models.SlipperType;
import com.gestion.today.presentation.dto.SlipperDTO;
import com.gestion.today.service.interfaces.CodTodayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/cod-today")
public class CodTodayController {
    @Autowired
    private CodTodayService codTodayService;

    @PostMapping("/{tableName}/{brand}/{company}")
    public String createCodToday(@PathVariable String tableName,
                                 @PathVariable String brand,
                                 @PathVariable String company,
                                 @RequestParam("file")MultipartFile file)throws IOException {
        return codTodayService.saveCodToday(tableName, brand, company,file);
    }

    @GetMapping("/{tableName}/{brand}/details/{codToday}")
    public ResponseEntity<SlipperDTO> getSlipperDetails(@PathVariable String tableName,
                                                        @PathVariable String brand,
                                                        @PathVariable String codToday){
        SlipperType slipperType;
        try{
            slipperType =  SlipperType.valueOf(tableName.toUpperCase());
        }catch (IllegalArgumentException e){
            return ResponseEntity.badRequest().build();
        }
        return codTodayService.detailsShoe(slipperType, brand, codToday)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    @GetMapping("update/{tableName}/{brand}/{codToday}")
    public ResponseEntity<String> updateGenerateQR(@PathVariable String tableName,
                                                   @PathVariable String brand,
                                                   @PathVariable String codToday){

        String response = codTodayService.updateQR(codToday,tableName,brand, null);
        return ResponseEntity.ok(response);

    }
}
