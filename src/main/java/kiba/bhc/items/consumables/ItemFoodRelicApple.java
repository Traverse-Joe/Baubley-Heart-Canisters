package kiba.bhc.items.consumables;

import kiba.bhc.BaubleyHeartCanisters;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

public class ItemFoodRelicApple extends net.minecraft.item.ItemFood{
    public ItemFoodRelicApple() {
        super( 2, 0.5F, false);
        this.setAlwaysEdible();
        this.setRegistryName("relic_apple");
        this.setUnlocalizedName("relic_apple");
        this.setCreativeTab(BaubleyHeartCanisters.CREATIVE_TAB);
    }

    @Override
    public void onFoodEaten(ItemStack stack, World worldIn, EntityPlayer player) {
        if(!worldIn.isRemote){
            player.addPotionEffect(new PotionEffect(MobEffects.RESISTANCE,20*60,1));
            player.addPotionEffect(new PotionEffect(MobEffects.STRENGTH,20*60,1));
            player.addPotionEffect(new PotionEffect(MobEffects.HASTE,20*60,1));
            player.heal(20);
        }
    }
}
