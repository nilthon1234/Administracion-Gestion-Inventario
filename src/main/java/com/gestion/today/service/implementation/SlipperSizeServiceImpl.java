package com.gestion.today.service.implementation;

import com.gestion.today.service.interfaces.SizeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class SlipperSizeServiceImpl {

    private final Map<String, SizeService<?>> sizeServiceMap;

    @Autowired
    public SlipperSizeServiceImpl(List<SizeService<?>> updaters){
        this.sizeServiceMap = updaters.stream()
                .collect(Collectors.toMap(SizeService::getEntityType, Function.identity()));
    }

    public Object updateSizes(String tableType, String brand, String codToday, Map<String,Integer> sizes){
        SizeService<?> updater = sizeServiceMap.get(tableType.toLowerCase());
        if (updater == null){
            throw new RuntimeException("Unknown table type: " + tableType);
        }
        return  updater.updateSizes(codToday, sizes);
    }

}
