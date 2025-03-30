package com.gestion.today.presentation.controller;

import com.gestion.today.service.exceptiones.InvalidSizeException;
import com.gestion.today.service.implementation.SlipperSizeServiceImpl;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/slipper")
public class SlipperController {

    private final SlipperSizeServiceImpl slipperSizeService;
    @PutMapping("/update-size/{tableType}/{brand}/{codToday}")
    public ResponseEntity<?>updateSizes(@PathVariable String tableType, @PathVariable String brand,
                                        @PathVariable String codToday, @RequestBody Map<String, Integer> size){
        try {
            Object updateEntity = slipperSizeService.updateSizes(tableType, brand, codToday, size);
            return ResponseEntity.ok(updateEntity);

        }catch (EntityNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }catch (InvalidSizeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error:" + e.getMessage());
        }
    }

    @PutMapping("/update-file/{tableType}/{brand}/{codToday}")
    public String updateSlipperFile(@PathVariable String tableType,
                                               @PathVariable String brand,
                                               @PathVariable String codToday,
                                               @RequestParam("file")MultipartFile file)throws IOException{
        return slipperSizeService.updateNameFile(tableType, brand, codToday, file);
    }
}
