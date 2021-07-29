package com.traverse.bhc.client.screens;

import com.mojang.blaze3d.vertex.PoseStack;
import com.traverse.bhc.common.BaubleyHeartCanisters;
import com.traverse.bhc.common.container.HeartAmuletContainer;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class HeartAmuletScreen extends AbstractContainerScreen<HeartAmuletContainer> {

    private static final ResourceLocation BACKGROUND_TEXTURE = new ResourceLocation(BaubleyHeartCanisters.MODID, "textures/gui/heart_amulet.png");

    public HeartAmuletScreen(HeartAmuletContainer container, Inventory inventory, Component title) {
        super(container, inventory, title);
    }

    @Override
    public void render(PoseStack stack, int mouseX, int mouseY, float partialTicks) {
       this.renderBackground(stack);
       super.render(stack, mouseX, mouseY, partialTicks);
       this.renderTooltip(stack, mouseX, mouseY);
    }

    @Override
    protected void renderBg(PoseStack stack, float partialTicks, int mouseX, int mouseY) {
        getMinecraft().getTextureManager().bindForSetup(BACKGROUND_TEXTURE);
        blit(stack, leftPos, topPos, 0, 0, imageWidth, imageHeight);
    }

}
