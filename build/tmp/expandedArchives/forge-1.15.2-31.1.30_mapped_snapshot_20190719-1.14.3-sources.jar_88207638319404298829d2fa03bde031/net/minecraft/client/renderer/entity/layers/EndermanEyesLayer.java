package net.minecraft.client.renderer.entity.layers;

import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.model.EndermanModel;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class EndermanEyesLayer<T extends LivingEntity> extends AbstractEyesLayer<T, EndermanModel<T>> {
   private static final RenderType field_229133_a_ = RenderType.func_228652_i_(new ResourceLocation("textures/entity/enderman/enderman_eyes.png"));

   public EndermanEyesLayer(IEntityRenderer<T, EndermanModel<T>> p_i50939_1_) {
      super(p_i50939_1_);
   }

   public RenderType func_225636_a_() {
      return field_229133_a_;
   }
}