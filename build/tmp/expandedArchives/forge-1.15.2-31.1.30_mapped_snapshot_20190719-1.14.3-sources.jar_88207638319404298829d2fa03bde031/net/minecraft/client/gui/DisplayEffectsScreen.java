package net.minecraft.client.gui;

import com.google.common.collect.Ordering;
import com.mojang.blaze3d.systems.RenderSystem;
import java.util.Collection;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.client.renderer.texture.PotionSpriteUploader;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.EffectUtils;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public abstract class DisplayEffectsScreen<T extends Container> extends ContainerScreen<T> {
   protected boolean hasActivePotionEffects;

   public DisplayEffectsScreen(T p_i51091_1_, PlayerInventory p_i51091_2_, ITextComponent p_i51091_3_) {
      super(p_i51091_1_, p_i51091_2_, p_i51091_3_);
   }

   protected void init() {
      super.init();
      this.updateActivePotionEffects();
   }

   protected void updateActivePotionEffects() {
      if (this.minecraft.player.getActivePotionEffects().isEmpty()) {
         this.guiLeft = (this.width - this.xSize) / 2;
         this.hasActivePotionEffects = false;
      } else {
         if (net.minecraftforge.common.MinecraftForge.EVENT_BUS.post(new net.minecraftforge.client.event.GuiScreenEvent.PotionShiftEvent(this))) this.guiLeft = (this.width - this.xSize) / 2; else
         this.guiLeft = 160 + (this.width - this.xSize - 200) / 2;
         this.hasActivePotionEffects = true;
      }

   }

   public void render(int p_render_1_, int p_render_2_, float p_render_3_) {
      super.render(p_render_1_, p_render_2_, p_render_3_);
      if (this.hasActivePotionEffects) {
         this.drawActivePotionEffects();
      }

   }

   /**
    * Display the potion effects list
    */
   private void drawActivePotionEffects() {
      int i = this.guiLeft - 124;
      Collection<EffectInstance> collection = this.minecraft.player.getActivePotionEffects();
      if (!collection.isEmpty()) {
         RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
         int j = 33;
         if (collection.size() > 5) {
            j = 132 / (collection.size() - 1);
         }

         Iterable<EffectInstance> iterable = collection.stream().filter( effectInstance -> effectInstance.shouldRender()).sorted().collect(java.util.stream.Collectors.toList());
         this.func_214079_a(i, j, iterable);
         this.func_214077_b(i, j, iterable);
         this.func_214078_c(i, j, iterable);
      }
   }

   private void func_214079_a(int p_214079_1_, int p_214079_2_, Iterable<EffectInstance> p_214079_3_) {
      this.minecraft.getTextureManager().bindTexture(INVENTORY_BACKGROUND);
      int i = this.guiTop;

      for(EffectInstance effectinstance : p_214079_3_) {
         RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
         this.blit(p_214079_1_, i, 0, 166, 140, 32);
         i += p_214079_2_;
      }

   }

   private void func_214077_b(int p_214077_1_, int p_214077_2_, Iterable<EffectInstance> p_214077_3_) {
      PotionSpriteUploader potionspriteuploader = this.minecraft.getPotionSpriteUploader();
      int i = this.guiTop;

      for(EffectInstance effectinstance : p_214077_3_) {
         Effect effect = effectinstance.getPotion();
         TextureAtlasSprite textureatlassprite = potionspriteuploader.getSprite(effect);
         this.minecraft.getTextureManager().bindTexture(textureatlassprite.func_229241_m_().func_229223_g_());
         blit(p_214077_1_ + 6, i + 7, this.getBlitOffset(), 18, 18, textureatlassprite);
         i += p_214077_2_;
      }

   }

   private void func_214078_c(int p_214078_1_, int p_214078_2_, Iterable<EffectInstance> p_214078_3_) {
      int i = this.guiTop;

      for(EffectInstance effectinstance : p_214078_3_) {
         effectinstance.renderInventoryEffect(this, p_214078_1_, i, this.getBlitOffset());
         if (!effectinstance.shouldRenderInvText()) { i += p_214078_2_; continue; }
         String s = I18n.format(effectinstance.getPotion().getName());
         if (effectinstance.getAmplifier() >= 1 && effectinstance.getAmplifier() <= 9) {
            s = s + ' ' + I18n.format("enchantment.level." + (effectinstance.getAmplifier() + 1));
         }

         this.font.drawStringWithShadow(s, (float)(p_214078_1_ + 10 + 18), (float)(i + 6), 16777215);
         String s1 = EffectUtils.getPotionDurationString(effectinstance, 1.0F);
         this.font.drawStringWithShadow(s1, (float)(p_214078_1_ + 10 + 18), (float)(i + 6 + 10), 8355711);
         i += p_214078_2_;
      }

   }
}