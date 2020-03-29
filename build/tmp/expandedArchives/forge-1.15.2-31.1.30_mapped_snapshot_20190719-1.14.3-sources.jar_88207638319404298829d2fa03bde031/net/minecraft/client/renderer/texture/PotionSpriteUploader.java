package net.minecraft.client.renderer.texture;

import java.util.stream.Stream;
import net.minecraft.potion.Effect;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class PotionSpriteUploader extends SpriteUploader {
   public PotionSpriteUploader(TextureManager p_i50908_1_) {
      super(p_i50908_1_, new ResourceLocation("textures/atlas/mob_effects.png"), "mob_effect");
   }

   protected Stream<ResourceLocation> func_225640_a_() {
      return Registry.EFFECTS.keySet().stream();
   }

   public TextureAtlasSprite getSprite(Effect p_215288_1_) {
      return this.getSprite(Registry.EFFECTS.getKey(p_215288_1_));
   }
}