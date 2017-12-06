package kiba.bhc.util;

import java.util.UUID;

/**
 * @author UpcraftLP
 */
public enum HeartType {
    RED(10, UUID.fromString("caa44aa0-9e6e-4a57-9759-d2f64abfb7d3")),
    ORANGE(20, UUID.fromString("147d8bca-a33c-440e-8985-22207747ffd8")),
    GREEN(30, UUID.fromString("85084e13-daa0-4340-aebc-5d1e1dedc3d9")),
    BLUE(40, UUID.fromString("268edd4a-e364-4afe-8e1e-7212734a7025"));

    public final UUID modifierID;
    public final int healAmount;

    HeartType(int healAmount, UUID uuid) {
        this.modifierID = uuid;
        this.healAmount = healAmount;
    }
}
