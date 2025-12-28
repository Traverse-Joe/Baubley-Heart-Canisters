package com.traverse.bhc.common.util;

public enum HeartType {
    RED(20,1),
    YELLOW(40,2),
    GREEN(60,3),
    BLUE(80,4),
    SOUL(0,0);

    public final int healAmount;
    public final int multiplier;

    HeartType(int healAmount, int multiplier) {
        this.healAmount = healAmount;
        this.multiplier = multiplier;
    }

    public int getHealAmount() {
        return healAmount;
    }

    public int getMultiplier() {
        return multiplier;
    }
}
