package com.traverse.bhc.common.util;

public enum HeartType {
    RED(20),
    YELLOW(40),
    GREEN(60),
    BLUE(80);

    public final int healAmount;

    HeartType(int healAmount) {
        this.healAmount = healAmount;
    }
}
