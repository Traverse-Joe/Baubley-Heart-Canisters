package com.traverse.bhc.common.items;

import com.traverse.bhc.common.BaubleyHeartCanisters;
import com.traverse.bhc.common.container.HeartAmuletContainer;
import com.traverse.bhc.common.init.RegistryHandler;
import com.traverse.bhc.common.util.HeartType;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Util;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.fml.network.NetworkHooks;

import java.util.List;

public class ItemHeartAmulet extends BaseItem implements INamedContainerProvider {

    public ItemHeartAmulet() {
        super(1);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
        if (!worldIn.isRemote() && !playerIn.isSneaking()) {
            NetworkHooks.openGui((ServerPlayerEntity) playerIn, this, buffer -> buffer.writeItemStack(playerIn.getHeldItem(handIn)));
        }

        return super.onItemRightClick(worldIn, playerIn, handIn);
    }

    public int[] getHeartCount(ItemStack stack) {
        if (stack.hasTag()) {
            CompoundNBT nbt = stack.getTag();
            if (nbt.contains(HeartAmuletContainer.HEART_AMOUNT, Constants.NBT.TAG_INT_ARRAY))
                return nbt.getIntArray(HeartAmuletContainer.HEART_AMOUNT);
        }

        return new int[HeartType.values().length];
    }

    @Override
    public ITextComponent getDisplayName() {
        return new TranslationTextComponent("container.bhc.heart_amulet");
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public Container createMenu(int id, PlayerInventory playerInventory, PlayerEntity playerEntity) {
        Hand hand = getHandForAmulet(playerEntity);
        return new HeartAmuletContainer(id, playerInventory, hand != null ? playerEntity.getHeldItem(hand) : ItemStack.EMPTY);
    }

    @Override
    public void addInformation(ItemStack stack, World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        super.addInformation(stack, worldIn, tooltip, flagIn);
        tooltip.add(new TranslationTextComponent(Util.makeTranslationKey("tooltip", new ResourceLocation(BaubleyHeartCanisters.MODID, "heartamulet"))).setStyle(Style.EMPTY.applyFormatting(TextFormatting.GOLD)));
    }

    public static Hand getHandForAmulet(PlayerEntity player) {
        if(player.getHeldItemMainhand().getItem() == RegistryHandler.HEART_AMULET.get())
            return Hand.MAIN_HAND;
        else if(player.getHeldItemOffhand().getItem() == RegistryHandler.HEART_AMULET.get())
            return Hand.OFF_HAND;

        return null;
    }
}
