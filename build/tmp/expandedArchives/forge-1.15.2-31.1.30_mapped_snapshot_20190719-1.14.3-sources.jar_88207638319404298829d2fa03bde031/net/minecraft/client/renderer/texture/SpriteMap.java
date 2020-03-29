package net.minecraft.client.renderer.texture;

import java.util.Collection;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import net.minecraft.client.renderer.model.Material;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class SpriteMap implements AutoCloseable {
   private final Map<ResourceLocation, AtlasTexture> field_229150_a_;

   public SpriteMap(Collection<AtlasTexture> p_i226042_1_) {
      this.field_229150_a_ = p_i226042_1_.stream().collect(Collectors.toMap(AtlasTexture::func_229223_g_, Function.identity()));
   }

   public AtlasTexture func_229152_a_(ResourceLocation p_229152_1_) {
      return this.field_229150_a_.get(p_229152_1_);
   }

   public TextureAtlasSprite func_229151_a_(Material p_229151_1_) {
      return this.field_229150_a_.get(p_229151_1_.func_229310_a_()).getSprite(p_229151_1_.func_229313_b_());
   }

   public void close() {
      this.field_229150_a_.values().forEach(AtlasTexture::clear);
      this.field_229150_a_.clear();
   }
}