package net.minecraft.client.renderer;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class RenderHelper {
   public static void func_227780_a_() {
      RenderSystem.enableLighting();
      RenderSystem.enableColorMaterial();
      RenderSystem.colorMaterial(1032, 5634);
   }

   /**
    * Disables the OpenGL lighting properties enabled by enableStandardItemLighting
    */
   public static void disableStandardItemLighting() {
      RenderSystem.disableLighting();
      RenderSystem.disableColorMaterial();
   }

   public static void func_227781_a_(Matrix4f p_227781_0_) {
      RenderSystem.setupLevelDiffuseLighting(p_227781_0_);
   }

   public static void func_227783_c_() {
      RenderSystem.setupGuiFlatDiffuseLighting();
   }

   public static void func_227784_d_() {
      RenderSystem.setupGui3DDiffuseLighting();
   }
}