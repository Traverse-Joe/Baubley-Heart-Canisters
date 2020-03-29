package net.minecraft.world.gen.feature.jigsaw;

import com.google.common.collect.Maps;
import java.util.Map;
import net.minecraft.util.ResourceLocation;

public class JigsawPatternRegistry {
   private final Map<ResourceLocation, JigsawPattern> field_214934_a = Maps.newHashMap();

   public JigsawPatternRegistry() {
      this.register(JigsawPattern.EMPTY);
   }

   public void register(JigsawPattern p_214932_1_) {
      this.field_214934_a.put(p_214932_1_.func_214947_b(), p_214932_1_);
   }

   public JigsawPattern get(ResourceLocation p_214933_1_) {
      JigsawPattern jigsawpattern = this.field_214934_a.get(p_214933_1_);
      return jigsawpattern != null ? jigsawpattern : JigsawPattern.INVALID;
   }
}