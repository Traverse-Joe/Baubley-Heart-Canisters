//package sora.bhc.client;
//
//import com.mojang.blaze3d.platform.GlStateManager;
//import net.minecraft.client.gui.screen.inventory.ContainerScreen;
//import net.minecraft.client.resources.I18n;
//import net.minecraft.entity.player.PlayerInventory;
//import net.minecraft.util.ResourceLocation;
//import net.minecraft.util.text.ITextComponent;
//import sora.bhc.Reference;
//import sora.bhc.container.HeartPendantContainer;
//
//public class HeartPendantScreen extends ContainerScreen<HeartPendantContainer> {
//
//  private static final ResourceLocation PENDANT_BACKGROUND = new ResourceLocation(Reference.MODID, "textures/gui/heart_pendant.png");
//
//    public HeartPendantScreen(HeartPendantContainer container, final PlayerInventory inventory, final ITextComponent text) {
//        super(container,inventory,text);
//    }
//
//  @Override
//  public void render(int mouseX, int mouseY, float partialTicks) {
//    if(minecraft != null){
//      renderBackground();
//      super.render(mouseX,mouseY,partialTicks);
//      renderHoveredToolTip(mouseX,mouseY);
//    }
//  }
//
//  @Override
//    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
//        GlStateManager.func_227637_a_(1.0F, 1.0F, 1.0F, 1.0F);
//        minecraft.getTextureManager().bindTexture(PENDANT_BACKGROUND);
//        int i = this.guiLeft;
//        int j = this.guiTop;
//      this.blit(i, j, 176, 166, xSize, ySize);
//        //drawModalRectWithCustomSizedTexture(i, j, 0, 0, this.xSize, this.ySize, 176, 166);
//    }
//
//    @Override
//    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
//        this.font.drawString(I18n.format("container.bhc.heart_pendant"), 3, 3, 4210752);
//    }
//}
