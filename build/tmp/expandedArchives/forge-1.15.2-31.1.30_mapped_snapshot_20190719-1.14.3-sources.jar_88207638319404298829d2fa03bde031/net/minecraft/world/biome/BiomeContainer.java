package net.minecraft.world.biome;

import javax.annotation.Nullable;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.provider.BiomeProvider;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class BiomeContainer implements BiomeManager.IBiomeReader {
   private static final Logger field_230029_d_ = LogManager.getLogger();
   private static final int field_227052_d_ = (int)Math.round(Math.log(16.0D) / Math.log(2.0D)) - 2;
   private static final int field_227053_e_ = (int)Math.round(Math.log(256.0D) / Math.log(2.0D)) - 2;
   public static final int field_227049_a_ = 1 << field_227052_d_ + field_227052_d_ + field_227053_e_;
   public static final int field_227050_b_ = (1 << field_227052_d_) - 1;
   public static final int field_227051_c_ = (1 << field_227053_e_) - 1;
   private final Biome[] field_227054_f_;

   public BiomeContainer(Biome[] p_i225779_1_) {
      this.field_227054_f_ = p_i225779_1_;
   }

   private BiomeContainer() {
      this(new Biome[field_227049_a_]);
   }

   public BiomeContainer(PacketBuffer p_i225778_1_) {
      this();

      for(int i = 0; i < this.field_227054_f_.length; ++i) {
         int j = p_i225778_1_.readInt();
         Biome biome = Registry.BIOME.getByValue(j);
         if (biome == null) {
            field_230029_d_.warn("Received invalid biome id: " + j);
            this.field_227054_f_[i] = Biomes.PLAINS;
         } else {
            this.field_227054_f_[i] = biome;
         }
      }

   }

   public BiomeContainer(ChunkPos p_i225776_1_, BiomeProvider p_i225776_2_) {
      this();
      int i = p_i225776_1_.getXStart() >> 2;
      int j = p_i225776_1_.getZStart() >> 2;

      for(int k = 0; k < this.field_227054_f_.length; ++k) {
         int l = k & field_227050_b_;
         int i1 = k >> field_227052_d_ + field_227052_d_ & field_227051_c_;
         int j1 = k >> field_227052_d_ & field_227050_b_;
         this.field_227054_f_[k] = p_i225776_2_.func_225526_b_(i + l, i1, j + j1);
      }

   }

   public BiomeContainer(ChunkPos p_i225777_1_, BiomeProvider p_i225777_2_, @Nullable int[] p_i225777_3_) {
      this();
      int i = p_i225777_1_.getXStart() >> 2;
      int j = p_i225777_1_.getZStart() >> 2;
      if (p_i225777_3_ != null) {
         for(int k = 0; k < p_i225777_3_.length; ++k) {
            this.field_227054_f_[k] = Registry.BIOME.getByValue(p_i225777_3_[k]);
            if (this.field_227054_f_[k] == null) {
               int l = k & field_227050_b_;
               int i1 = k >> field_227052_d_ + field_227052_d_ & field_227051_c_;
               int j1 = k >> field_227052_d_ & field_227050_b_;
               this.field_227054_f_[k] = p_i225777_2_.func_225526_b_(i + l, i1, j + j1);
            }
         }
      } else {
         for(int k1 = 0; k1 < this.field_227054_f_.length; ++k1) {
            int l1 = k1 & field_227050_b_;
            int i2 = k1 >> field_227052_d_ + field_227052_d_ & field_227051_c_;
            int j2 = k1 >> field_227052_d_ & field_227050_b_;
            this.field_227054_f_[k1] = p_i225777_2_.func_225526_b_(i + l1, i2, j + j2);
         }
      }

   }

   public int[] func_227055_a_() {
      int[] aint = new int[this.field_227054_f_.length];

      for(int i = 0; i < this.field_227054_f_.length; ++i) {
         aint[i] = Registry.BIOME.getId(this.field_227054_f_[i]);
      }

      return aint;
   }

   public void func_227056_a_(PacketBuffer p_227056_1_) {
      for(Biome biome : this.field_227054_f_) {
         p_227056_1_.writeInt(Registry.BIOME.getId(biome));
      }

   }

   public BiomeContainer func_227057_b_() {
      return new BiomeContainer((Biome[])this.field_227054_f_.clone());
   }

   public Biome func_225526_b_(int p_225526_1_, int p_225526_2_, int p_225526_3_) {
      int i = p_225526_1_ & field_227050_b_;
      int j = MathHelper.clamp(p_225526_2_, 0, field_227051_c_);
      int k = p_225526_3_ & field_227050_b_;
      return this.field_227054_f_[j << field_227052_d_ + field_227052_d_ | k << field_227052_d_ | i];
   }
}