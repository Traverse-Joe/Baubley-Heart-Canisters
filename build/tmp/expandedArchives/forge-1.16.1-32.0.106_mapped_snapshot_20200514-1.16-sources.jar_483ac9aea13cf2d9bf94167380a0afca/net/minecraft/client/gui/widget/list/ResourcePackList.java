package net.minecraft.client.gui.widget.list;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.gui.screen.ConfirmScreen;
import net.minecraft.client.gui.screen.PackLoadingManager;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.resources.PackCompatibility;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.ITextProperties;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ResourcePackList extends ExtendedList<ResourcePackList.ResourcePackEntry> {
   private static final ResourceLocation field_214367_b = new ResourceLocation("textures/gui/resource_packs.png");
   private static final ITextComponent field_214368_c = new TranslationTextComponent("pack.incompatible");
   private static final ITextComponent field_214369_d = new TranslationTextComponent("pack.incompatible.confirm.title");
   private final ITextComponent field_214370_e;

   public ResourcePackList(Minecraft p_i241200_1_, int p_i241200_2_, int p_i241200_3_, ITextComponent p_i241200_4_) {
      super(p_i241200_1_, p_i241200_2_, p_i241200_3_, 32, p_i241200_3_ - 55 + 4, 36);
      this.field_214370_e = p_i241200_4_;
      this.field_230676_m_ = false;
      this.func_230944_a_(true, (int)(9.0F * 1.5F));
   }

   protected void func_230448_a_(MatrixStack p_230448_1_, int p_230448_2_, int p_230448_3_, Tessellator p_230448_4_) {
      ITextComponent itextcomponent = (new StringTextComponent("")).func_230529_a_(this.field_214370_e).func_240701_a_(TextFormatting.UNDERLINE, TextFormatting.BOLD);
      this.field_230668_b_.fontRenderer.func_238422_b_(p_230448_1_, itextcomponent, (float)(p_230448_2_ + this.field_230670_d_ / 2 - this.field_230668_b_.fontRenderer.func_238414_a_(itextcomponent) / 2), (float)Math.min(this.field_230672_i_ + 3, p_230448_3_), 16777215);
   }

   public int func_230949_c_() {
      return this.field_230670_d_;
   }

   protected int func_230952_d_() {
      return this.field_230674_k_ - 6;
   }

   @OnlyIn(Dist.CLIENT)
   public static class ResourcePackEntry extends ExtendedList.AbstractListEntry<ResourcePackList.ResourcePackEntry> {
      private ResourcePackList field_214430_c;
      protected final Minecraft field_214428_a;
      protected final Screen field_214429_b;
      private final PackLoadingManager.IPack field_214431_d;

      public ResourcePackEntry(Minecraft p_i241201_1_, ResourcePackList p_i241201_2_, Screen p_i241201_3_, PackLoadingManager.IPack p_i241201_4_) {
         this.field_214428_a = p_i241201_1_;
         this.field_214429_b = p_i241201_3_;
         this.field_214431_d = p_i241201_4_;
         this.field_214430_c = p_i241201_2_;
      }

      public void func_230432_a_(MatrixStack p_230432_1_, int p_230432_2_, int p_230432_3_, int p_230432_4_, int p_230432_5_, int p_230432_6_, int p_230432_7_, int p_230432_8_, boolean p_230432_9_, float p_230432_10_) {
         PackCompatibility packcompatibility = this.field_214431_d.func_230460_a_();
         if (!packcompatibility.isCompatible()) {
            RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
            AbstractGui.func_238467_a_(p_230432_1_, p_230432_4_ - 1, p_230432_3_ - 1, p_230432_4_ + p_230432_5_ - 9, p_230432_3_ + p_230432_6_ + 1, -8978432);
         }

         this.field_214431_d.func_230461_a_(this.field_214428_a.getTextureManager());
         RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
         AbstractGui.func_238463_a_(p_230432_1_, p_230432_4_, p_230432_3_, 0.0F, 0.0F, 32, 32, 32, 32);
         ITextComponent itextcomponent = this.field_214431_d.func_230462_b_();
         ITextProperties itextproperties = this.field_214431_d.func_238874_e_();
         if (this.func_238920_a_() && (this.field_214428_a.gameSettings.touchscreen || p_230432_9_)) {
            this.field_214428_a.getTextureManager().bindTexture(ResourcePackList.field_214367_b);
            AbstractGui.func_238467_a_(p_230432_1_, p_230432_4_, p_230432_3_, p_230432_4_ + 32, p_230432_3_ + 32, -1601138544);
            RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
            int i = p_230432_7_ - p_230432_4_;
            int j = p_230432_8_ - p_230432_3_;
            if (!packcompatibility.isCompatible()) {
               itextcomponent = ResourcePackList.field_214368_c;
               itextproperties = packcompatibility.getDescription();
            }

            if (this.field_214431_d.func_238875_m_()) {
               if (i < 32) {
                  AbstractGui.func_238463_a_(p_230432_1_, p_230432_4_, p_230432_3_, 0.0F, 32.0F, 32, 32, 256, 256);
               } else {
                  AbstractGui.func_238463_a_(p_230432_1_, p_230432_4_, p_230432_3_, 0.0F, 0.0F, 32, 32, 256, 256);
               }
            } else {
               if (this.field_214431_d.func_238876_n_()) {
                  if (i < 16) {
                     AbstractGui.func_238463_a_(p_230432_1_, p_230432_4_, p_230432_3_, 32.0F, 32.0F, 32, 32, 256, 256);
                  } else {
                     AbstractGui.func_238463_a_(p_230432_1_, p_230432_4_, p_230432_3_, 32.0F, 0.0F, 32, 32, 256, 256);
                  }
               }

               if (this.field_214431_d.func_230469_o_()) {
                  if (i < 32 && i > 16 && j < 16) {
                     AbstractGui.func_238463_a_(p_230432_1_, p_230432_4_, p_230432_3_, 96.0F, 32.0F, 32, 32, 256, 256);
                  } else {
                     AbstractGui.func_238463_a_(p_230432_1_, p_230432_4_, p_230432_3_, 96.0F, 0.0F, 32, 32, 256, 256);
                  }
               }

               if (this.field_214431_d.func_230470_p_()) {
                  if (i < 32 && i > 16 && j > 16) {
                     AbstractGui.func_238463_a_(p_230432_1_, p_230432_4_, p_230432_3_, 64.0F, 32.0F, 32, 32, 256, 256);
                  } else {
                     AbstractGui.func_238463_a_(p_230432_1_, p_230432_4_, p_230432_3_, 64.0F, 0.0F, 32, 32, 256, 256);
                  }
               }
            }
         }

         int l = this.field_214428_a.fontRenderer.func_238414_a_(itextcomponent);
         if (l > 157) {
            ITextProperties itextproperties1 = ITextProperties.func_240655_a_(this.field_214428_a.fontRenderer.func_238417_a_(itextcomponent, 157 - this.field_214428_a.fontRenderer.getStringWidth("...")), ITextProperties.func_240652_a_("..."));
            this.field_214428_a.fontRenderer.func_238407_a_(p_230432_1_, itextproperties1, (float)(p_230432_4_ + 32 + 2), (float)(p_230432_3_ + 1), 16777215);
         } else {
            this.field_214428_a.fontRenderer.func_238407_a_(p_230432_1_, itextcomponent, (float)(p_230432_4_ + 32 + 2), (float)(p_230432_3_ + 1), 16777215);
         }

         this.field_214428_a.fontRenderer.func_238407_a_(p_230432_1_, itextcomponent, (float)(p_230432_4_ + 32 + 2), (float)(p_230432_3_ + 1), 16777215);
         List<ITextProperties> list = this.field_214428_a.fontRenderer.func_238425_b_(itextproperties, 157);

         for(int k = 0; k < 2 && k < list.size(); ++k) {
            this.field_214428_a.fontRenderer.func_238407_a_(p_230432_1_, list.get(k), (float)(p_230432_4_ + 32 + 2), (float)(p_230432_3_ + 12 + 10 * k), 8421504);
         }

      }

      private boolean func_238920_a_() {
         return !this.field_214431_d.func_230465_f_() || !this.field_214431_d.func_230466_g_();
      }

      public boolean func_231044_a_(double p_231044_1_, double p_231044_3_, int p_231044_5_) {
         double d0 = p_231044_1_ - (double)this.field_214430_c.func_230968_n_();
         double d1 = p_231044_3_ - (double)this.field_214430_c.func_230962_i_(this.field_214430_c.func_231039_at__().indexOf(this));
         if (this.func_238920_a_() && d0 <= 32.0D) {
            if (this.field_214431_d.func_238875_m_()) {
               PackCompatibility packcompatibility = this.field_214431_d.func_230460_a_();
               if (packcompatibility.isCompatible()) {
                  this.field_214431_d.func_230471_h_();
               } else {
                  ITextComponent itextcomponent = packcompatibility.getConfirmMessage();
                  this.field_214428_a.displayGuiScreen(new ConfirmScreen((p_238921_1_) -> {
                     this.field_214428_a.displayGuiScreen(this.field_214429_b);
                     if (p_238921_1_) {
                        this.field_214431_d.func_230471_h_();
                     }

                  }, ResourcePackList.field_214369_d, itextcomponent));
               }

               return true;
            }

            if (d0 < 16.0D && this.field_214431_d.func_238876_n_()) {
               this.field_214431_d.func_230472_i_();
               return true;
            }

            if (d0 > 16.0D && d1 < 16.0D && this.field_214431_d.func_230469_o_()) {
               this.field_214431_d.func_230467_j_();
               return true;
            }

            if (d0 > 16.0D && d1 > 16.0D && this.field_214431_d.func_230470_p_()) {
               this.field_214431_d.func_230468_k_();
               return true;
            }
         }

         return false;
      }
   }
}