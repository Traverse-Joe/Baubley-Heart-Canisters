package net.minecraft.entity.player;

import java.util.Arrays;
import java.util.Comparator;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public enum ChatVisibility {
   FULL(0, "options.chat.visibility.full"),
   SYSTEM(1, "options.chat.visibility.system"),
   HIDDEN(2, "options.chat.visibility.hidden");

   private static final ChatVisibility[] field_221255_d = Arrays.stream(values()).sorted(Comparator.comparingInt(ChatVisibility::func_221254_a)).toArray((p_221253_0_) -> {
      return new ChatVisibility[p_221253_0_];
   });
   private final int field_221256_e;
   private final String field_221257_f;

   private ChatVisibility(int p_i50176_3_, String p_i50176_4_) {
      this.field_221256_e = p_i50176_3_;
      this.field_221257_f = p_i50176_4_;
   }

   public int func_221254_a() {
      return this.field_221256_e;
   }

   @OnlyIn(Dist.CLIENT)
   public String func_221251_b() {
      return this.field_221257_f;
   }

   @OnlyIn(Dist.CLIENT)
   public static ChatVisibility func_221252_a(int p_221252_0_) {
      return field_221255_d[MathHelper.normalizeAngle(p_221252_0_, field_221255_d.length)];
   }
}