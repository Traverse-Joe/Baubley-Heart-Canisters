package com.traverse.bhc.common.items;

import com.traverse.bhc.common.config.ConfigHandler;
import com.traverse.bhc.common.util.HeartType;

public class BaseHeartCanister extends BaseItem {

    public HeartType type;

    public BaseHeartCanister(HeartType type) {
        super(ConfigHandler.general.heartStackSize.get());
        this.type = type;
    }
}
