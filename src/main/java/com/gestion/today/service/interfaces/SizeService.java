package com.gestion.today.service.interfaces;

import java.util.Map;

public interface SizeService<T> {

    String getEntityType();
    T updateSizes(String codToday, Map<String, Integer> sizes);



}
