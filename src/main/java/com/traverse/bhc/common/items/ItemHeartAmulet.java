package com.traverse.bhc.common.items;

import com.traverse.bhc.common.container.HeartAmuletContainer;
import com.traverse.bhc.common.util.RegistryHandler;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;
import top.theillusivec4.curios.api.type.capability.ICurio;

public class ItemHeartAmulet extends BaseItem {

    public ItemHeartAmulet(){
        super();
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
        ItemStack stack = playerIn.getHeldItem(handIn);
        if(playerIn.isSneaking()){
            if(!worldIn.isRemote){
               NetworkHooks.openGui((ServerPlayerEntity) playerIn, new INamedContainerProvider() {
                    @Override
                    public ITextComponent getDisplayName() {
                        return new TranslationTextComponent("container.bhc.heart_amulet");
                    }

                    @Override
                    public Container createMenu(int i, PlayerInventory playerInventory, PlayerEntity playerEntity) {
                        return new HeartAmuletContainer(RegistryHandler.HEART_AMUlET_CONTAINER.get(), i, playerInventory, new ItemStack(RegistryHandler.HEART_AMULET.get()));
                    }
                });

            }
        }
        return super.onItemRightClick(worldIn, playerIn, handIn);
    }
}
