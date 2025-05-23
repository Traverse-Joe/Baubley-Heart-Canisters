package com.traverse.bhc.common.items;

import com.traverse.bhc.common.config.ConfigHandler;
import com.traverse.bhc.common.util.HeartType;

public class BaseHeartCanister extends BaseItem {

    private final HeartType type;

    public BaseHeartCanister(Properties properties, HeartType type) {
        super(properties, ConfigHandler.general.heartStackSize.get());
        this.type = type;
    }

    public HeartType getType() {
        return type;
    }
}
