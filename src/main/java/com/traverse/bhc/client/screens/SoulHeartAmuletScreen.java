package com.traverse.bhc.client.screens;

import com.traverse.bhc.common.BaubleyHeartCanisters;
import com.traverse.bhc.common.container.SoulHeartAmuletContainer;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.player.Inventory;

public class SoulHeartAmuletScreen extends AbstractContainerScreen<SoulHeartAmuletContainer> {
    private static final Identifier BACKGROUND_TEXTURE = BaubleyHeartCanisters.id("textures/gui/soul_heart_amulet.png");

    public SoulHeartAmuletScreen(SoulHeartAmuletContainer container, Inventory inventory, Component title) {
        super(container, inventory, title);
    }

    @Override
    public void extractBackground(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float partialTicks) {
        graphics.blit(RenderPipelines.GUI_TEXTURED, BACKGROUND_TEXTURE, leftPos, topPos, 0, 0, imageWidth, imageHeight, 256, 256);
    }

    @Override
    public void extractRenderState(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float partialTicks) {
        super.extractRenderState(graphics, mouseX, mouseY, partialTicks);
        extractTooltip(graphics, mouseX, mouseY);
    }
}
