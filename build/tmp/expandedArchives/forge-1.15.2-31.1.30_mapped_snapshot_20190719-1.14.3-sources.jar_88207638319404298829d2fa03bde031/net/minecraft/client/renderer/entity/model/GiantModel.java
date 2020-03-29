package net.minecraft.client.renderer.entity.model;

import net.minecraft.entity.monster.GiantEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class GiantModel extends AbstractZombieModel<GiantEntity> {
   public GiantModel() {
      this(0.0F, false);
   }

   public GiantModel(float p_i51066_1_, boolean p_i51066_2_) {
      super(p_i51066_1_, 0.0F, 64, p_i51066_2_ ? 32 : 64);
   }

   public boolean func_212850_a_(GiantEntity p_212850_1_) {
      return false;
   }
}