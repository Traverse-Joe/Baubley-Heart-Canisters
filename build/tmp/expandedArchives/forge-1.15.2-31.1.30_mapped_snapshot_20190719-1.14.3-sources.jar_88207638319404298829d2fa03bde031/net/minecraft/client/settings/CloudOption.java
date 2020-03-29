package net.minecraft.client.settings;

import java.util.Arrays;
import java.util.Comparator;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public enum CloudOption {
   OFF(0, "options.off"),
   FAST(1, "options.clouds.fast"),
   FANCY(2, "options.clouds.fancy");

   private static final CloudOption[] BY_ID = Arrays.stream(values()).sorted(Comparator.comparingInt(CloudOption::func_216806_a)).toArray((p_216805_0_) -> {
      return new CloudOption[p_216805_0_];
   });
   private final int id;
   private final String field_216809_f;

   private CloudOption(int id, String p_i51166_4_) {
      this.id = id;
      this.field_216809_f = p_i51166_4_;
   }

   public int func_216806_a() {
      return this.id;
   }

   public String func_216803_b() {
      return this.field_216809_f;
   }

   public static CloudOption byId(int id) {
      return BY_ID[MathHelper.normalizeAngle(id, BY_ID.length)];
   }
}