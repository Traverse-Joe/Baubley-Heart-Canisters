package net.minecraft.client.settings;

import java.util.Arrays;
import java.util.Comparator;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public enum AmbientOcclusionStatus {
   OFF(0, "options.ao.off"),
   MIN(1, "options.ao.min"),
   MAX(2, "options.ao.max");

   private static final AmbientOcclusionStatus[] field_216573_d = Arrays.stream(values()).sorted(Comparator.comparingInt(AmbientOcclusionStatus::func_216572_a)).toArray((p_216571_0_) -> {
      return new AmbientOcclusionStatus[p_216571_0_];
   });
   private final int field_216574_e;
   private final String field_216575_f;

   private AmbientOcclusionStatus(int p_i51169_3_, String p_i51169_4_) {
      this.field_216574_e = p_i51169_3_;
      this.field_216575_f = p_i51169_4_;
   }

   public int func_216572_a() {
      return this.field_216574_e;
   }

   public String func_216569_b() {
      return this.field_216575_f;
   }

   public static AmbientOcclusionStatus func_216570_a(int p_216570_0_) {
      return field_216573_d[MathHelper.normalizeAngle(p_216570_0_, field_216573_d.length)];
   }
}