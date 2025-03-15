package com.gestion.today.service.interfaces;

import com.gestion.today.persistence.models.Baby;

import java.util.Map;

public interface ServiceBaby {

    Baby saveBaby(Baby baby);
    Baby updateZise(String codToday, Map<String, Integer> zises);
}
