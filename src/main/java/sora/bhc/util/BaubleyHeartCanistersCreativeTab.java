package sora.bhc.util;

import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import sora.bhc.BaubleyHeartCanisters;
import sora.bhc.init.ModItems;

public class BaubleyHeartCanistersCreativeTab extends ItemGroup {

  private static final BaubleyHeartCanistersCreativeTab INSTANCE = new BaubleyHeartCanistersCreativeTab();

  public BaubleyHeartCanistersCreativeTab(){
    super(BaubleyHeartCanisters.MODID);
  }

  public static  BaubleyHeartCanistersCreativeTab getInstance(){
    return INSTANCE;
  }
  @Override
  public ItemStack createIcon() {
    return new ItemStack(ModItems.RED_HEART_CANISTER);
  }
}
