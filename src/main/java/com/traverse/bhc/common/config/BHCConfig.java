package com.traverse.bhc.common.config;

import com.google.common.collect.Maps;

import java.util.HashMap;
import java.util.Map;

public class BHCConfig {

    public Map<String, Map<String, Double>> heartEntries = Maps.newHashMap();

    public void addEntrytoMap(String heartType, String entityName, double chance) {
        heartEntries.compute(heartType, (s, stringDoubleMap) -> {
            if (stringDoubleMap == null) stringDoubleMap = new HashMap<>();
            stringDoubleMap.put(entityName, chance);
            return stringDoubleMap;
        });
    }

    public Map<String, Double> getHeartTypeEntries(String type) {
        return heartEntries.get(type);
    }
}
