package com.traverse.bhc.client.screens;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.traverse.bhc.common.BaubleyHeartCanisters;
import com.traverse.bhc.common.container.BladeOfVitalityContainer;
import com.traverse.bhc.common.container.HeartAmuletContainer;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class BladeOfVitalityScreen extends AbstractContainerScreen<BladeOfVitalityContainer> {

    private static final ResourceLocation BACKGROUND_TEXTURE = new ResourceLocation(BaubleyHeartCanisters.MODID, "textures/gui/blade_of_vitality.png");

    public BladeOfVitalityScreen(BladeOfVitalityContainer container, Inventory inventory, Component title) {
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
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, this.BACKGROUND_TEXTURE);
        blit(stack, leftPos, topPos, 0, 0, imageWidth, imageHeight);
    }
}
