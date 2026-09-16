package com.traverse.bhc.client.model;

import com.geckolib.model.DefaultedGeoModel;
import com.traverse.bhc.common.BaubleyHeartCanisters;
import com.traverse.bhc.common.items.GoldenPaladinArmorItem;

public class GoldenPaladinArmorModel extends DefaultedGeoModel<GoldenPaladinArmorItem> {
    public GoldenPaladinArmorModel() {
        super(BaubleyHeartCanisters.id("golden_paladin_armor"));
    }

    @Override
    protected String subtype() {
        return "armor";
    }
}
