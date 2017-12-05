package kiba.bhc.items;

import baubles.api.BaubleType;
import baubles.api.IBauble;
import net.minecraft.item.ItemStack;

public class ItemHeartAmulet extends BaseItem implements IBauble {
    public ItemHeartAmulet() {
        super("heart_amulet");

    }

    @Override
    public BaubleType getBaubleType(ItemStack itemstack) {
        return BaubleType.AMULET;
    }
}
