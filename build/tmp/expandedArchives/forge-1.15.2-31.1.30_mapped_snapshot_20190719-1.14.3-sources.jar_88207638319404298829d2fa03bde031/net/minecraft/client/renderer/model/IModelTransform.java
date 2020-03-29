package net.minecraft.client.renderer.model;

import net.minecraft.client.renderer.TransformationMatrix;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public interface IModelTransform extends net.minecraftforge.client.extensions.IForgeModelTransform {
   default TransformationMatrix func_225615_b_() {
      return TransformationMatrix.func_227983_a_();
   }

   default boolean isUvLock() {
      return false;
   }
}