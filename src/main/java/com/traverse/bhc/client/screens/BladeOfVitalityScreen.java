package com.traverse.bhc.client.screens;

import com.traverse.bhc.common.BaubleyHeartCanisters;
import com.traverse.bhc.common.container.BladeOfVitalityContainer;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class BladeOfVitalityScreen extends AbstractContainerScreen<BladeOfVitalityContainer> {

    private static final ResourceLocation BACKGROUND_TEXTURE = BaubleyHeartCanisters.id("textures/gui/blade_of_vitality.png");

    public BladeOfVitalityScreen(BladeOfVitalityContainer container, Inventory inventory, Component title) {
        super(container, inventory, title);
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float partialTicks, int mouseX, int mouseY) {
        guiGraphics.blit(RenderType::guiTextured, BACKGROUND_TEXTURE, leftPos, topPos, 0, 0, imageWidth, imageHeight, 256, 256);
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
        this.renderBg(guiGraphics, partialTicks, mouseX, mouseY);
        super.render(guiGraphics, mouseX, mouseY, partialTicks);
        this.renderTooltip(guiGraphics, mouseX, mouseY);
    }
}
