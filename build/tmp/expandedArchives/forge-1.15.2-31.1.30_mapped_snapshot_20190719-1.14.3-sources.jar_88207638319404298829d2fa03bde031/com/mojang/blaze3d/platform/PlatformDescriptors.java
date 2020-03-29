package com.mojang.blaze3d.platform;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class PlatformDescriptors {
   public static String func_227774_a_() {
      return GlStateManager.func_227610_C_(7936);
   }

   public static String func_227775_b_() {
      return GLX._getCpuInfo();
   }

   public static String func_227776_c_() {
      return GlStateManager.func_227610_C_(7937);
   }

   public static String func_227777_d_() {
      return GlStateManager.func_227610_C_(7938);
   }
}