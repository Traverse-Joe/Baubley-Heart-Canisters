package kiba.bhc.gui;

import kiba.bhc.Reference;
import kiba.bhc.gui.container.ContainerPendant;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;

/**
 * @author UpcraftLP
 */
public class GuiPendant extends GuiContainer {

    private static final ResourceLocation PENDAT_BACKGROUND = new ResourceLocation(Reference.MODID, "textures/gui/heart_pendant.png");

    public GuiPendant(ContainerPendant inventorySlotsIn) {
        super(inventorySlotsIn);
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.drawDefaultBackground();
        super.drawScreen(mouseX, mouseY, partialTicks);
        this.renderHoveredToolTip(mouseX, mouseY);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(PENDAT_BACKGROUND);
        int i = this.guiLeft;
        int j = this.guiTop;
        drawModalRectWithCustomSizedTexture(i, j, 0, 0, this.xSize, this.ySize, 176, 166);
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        this.fontRenderer.drawString(I18n.format("container.bhc.heart_pendant"), 3, 3, 4210752);
    }
}
