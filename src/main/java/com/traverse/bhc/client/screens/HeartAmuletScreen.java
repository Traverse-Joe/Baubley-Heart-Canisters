package com.traverse.bhc.client.screens;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.traverse.bhc.common.BaubleyHeartCanisters;
import com.traverse.bhc.common.container.HeartAmuletContainer;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class HeartAmuletScreen extends ContainerScreen<HeartAmuletContainer> {

    private static final ResourceLocation BACKGROUND_TEXTURE = new ResourceLocation(BaubleyHeartCanisters.MODID, "textures/gui/heart_amulet.png");

    public HeartAmuletScreen(HeartAmuletContainer container, PlayerInventory inventory, ITextComponent title) {
        super(container, inventory, title);
    }

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(matrixStack);
        super.render(matrixStack, mouseX, mouseY, partialTicks);
        this.renderHoveredTooltip(matrixStack, mouseX, mouseY);
    }

    @Override
    public void drawGuiContainerBackgroundLayer(MatrixStack matrixStack, float partialTicks, int mouseX, int mouseY) {
        getMinecraft().getTextureManager().bindTexture(BACKGROUND_TEXTURE);
        blit(matrixStack, guiLeft, guiTop, 0, 0, xSize, ySize);
    }
}
