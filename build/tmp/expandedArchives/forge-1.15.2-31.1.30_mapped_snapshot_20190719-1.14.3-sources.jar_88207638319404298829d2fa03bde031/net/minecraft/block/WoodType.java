package net.minecraft.block;

import it.unimi.dsi.fastutil.objects.ObjectArraySet;
import java.util.Set;
import java.util.stream.Stream;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class WoodType {
   private static final Set<WoodType> field_227044_g_ = new ObjectArraySet<>();
   public static final WoodType field_227038_a_ = func_227047_a_(new WoodType("oak"));
   public static final WoodType field_227039_b_ = func_227047_a_(new WoodType("spruce"));
   public static final WoodType field_227040_c_ = func_227047_a_(new WoodType("birch"));
   public static final WoodType field_227041_d_ = func_227047_a_(new WoodType("acacia"));
   public static final WoodType field_227042_e_ = func_227047_a_(new WoodType("jungle"));
   public static final WoodType field_227043_f_ = func_227047_a_(new WoodType("dark_oak"));
   private final String field_227045_h_;

   protected WoodType(String p_i225775_1_) {
      this.field_227045_h_ = p_i225775_1_;
   }

   private static WoodType func_227047_a_(WoodType p_227047_0_) {
      field_227044_g_.add(p_227047_0_);
      return p_227047_0_;
   }

   @OnlyIn(Dist.CLIENT)
   public static Stream<WoodType> func_227046_a_() {
      return field_227044_g_.stream();
   }

   @OnlyIn(Dist.CLIENT)
   public String func_227048_b_() {
      return this.field_227045_h_;
   }
}