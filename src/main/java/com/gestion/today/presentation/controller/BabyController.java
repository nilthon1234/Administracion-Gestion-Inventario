package com.gestion.today.presentation.controller;

import com.gestion.today.persistence.models.Baby;
import com.gestion.today.service.interfaces.ServiceBaby;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/baby")
public class BabyController {
    @Autowired
    private  ServiceBaby serviceBaby;

    @PutMapping("/update-zise/{codToday}")
    public Baby updateZIses (@PathVariable String codToday, @RequestBody Map<String, Integer>zises){
        return serviceBaby.updateZise(codToday, zises);
    }
}
