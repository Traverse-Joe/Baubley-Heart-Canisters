package net.minecraft.client.renderer;

import java.util.Collection;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class StitcherException extends RuntimeException {
   private final Collection<TextureAtlasSprite.Info> field_225332_a;

   public StitcherException(TextureAtlasSprite.Info p_i226046_1_, Collection<TextureAtlasSprite.Info> p_i226046_2_) {
      super(String.format("Unable to fit: %s - size: %dx%d - Maybe try a lower resolution resourcepack?", p_i226046_1_.func_229248_a_(), p_i226046_1_.func_229250_b_(), p_i226046_1_.func_229252_c_()));
      this.field_225332_a = p_i226046_2_;
   }

   public Collection<TextureAtlasSprite.Info> func_225331_a() {
      return this.field_225332_a;
   }
}