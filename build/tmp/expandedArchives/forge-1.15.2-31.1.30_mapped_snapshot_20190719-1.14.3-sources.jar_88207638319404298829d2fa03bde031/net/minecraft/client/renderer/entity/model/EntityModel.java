package net.minecraft.client.renderer.entity.model;

import java.util.function.Function;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.model.Model;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public abstract class EntityModel<T extends Entity> extends Model {
   public float swingProgress;
   public boolean isSitting;
   public boolean isChild = true;

   protected EntityModel() {
      this(RenderType::func_228640_c_);
   }

   protected EntityModel(Function<ResourceLocation, RenderType> p_i225945_1_) {
      super(p_i225945_1_);
   }

   public abstract void func_225597_a_(T p_225597_1_, float p_225597_2_, float p_225597_3_, float p_225597_4_, float p_225597_5_, float p_225597_6_);

   public void setLivingAnimations(T entityIn, float limbSwing, float limbSwingAmount, float partialTick) {
   }

   public void setModelAttributes(EntityModel<T> p_217111_1_) {
      p_217111_1_.swingProgress = this.swingProgress;
      p_217111_1_.isSitting = this.isSitting;
      p_217111_1_.isChild = this.isChild;
   }
}