package net.minecraft.client.renderer;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class Vector3d {
   public double x;
   public double y;
   public double z;

   public Vector3d(double p_i51794_1_, double p_i51794_3_, double p_i51794_5_) {
      this.x = p_i51794_1_;
      this.y = p_i51794_3_;
      this.z = p_i51794_5_;
   }
}