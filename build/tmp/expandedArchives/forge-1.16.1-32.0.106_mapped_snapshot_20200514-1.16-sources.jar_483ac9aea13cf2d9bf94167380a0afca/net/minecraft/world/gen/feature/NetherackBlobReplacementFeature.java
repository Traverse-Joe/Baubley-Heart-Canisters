package net.minecraft.world.gen.feature;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.vector.Vector3i;

public class NetherackBlobReplacementFeature implements IFeatureConfig {
   public static final Codec<NetherackBlobReplacementFeature> field_236608_a_ = RecordCodecBuilder.create((p_236614_0_) -> {
      return p_236614_0_.group(BlockState.field_235877_b_.fieldOf("target").forGetter((p_236617_0_) -> {
         return p_236617_0_.field_236609_b_;
      }), BlockState.field_235877_b_.fieldOf("state").forGetter((p_236616_0_) -> {
         return p_236616_0_.field_236610_c_;
      }), Vector3i.field_239781_c_.fieldOf("minimum_reach").forGetter((p_236615_0_) -> {
         return p_236615_0_.field_236611_d_;
      }), Vector3i.field_239781_c_.fieldOf("maximum_reach").forGetter((p_236613_0_) -> {
         return p_236613_0_.field_236612_e_;
      })).apply(p_236614_0_, NetherackBlobReplacementFeature::new);
   });
   public final BlockState field_236609_b_;
   public final BlockState field_236610_c_;
   public final Vector3i field_236611_d_;
   public final Vector3i field_236612_e_;

   public NetherackBlobReplacementFeature(BlockState p_i232015_1_, BlockState p_i232015_2_, Vector3i p_i232015_3_, Vector3i p_i232015_4_) {
      this.field_236609_b_ = p_i232015_1_;
      this.field_236610_c_ = p_i232015_2_;
      this.field_236611_d_ = p_i232015_3_;
      this.field_236612_e_ = p_i232015_4_;
   }

   public static class Builder {
      private BlockState field_236618_a_ = Blocks.AIR.getDefaultState();
      private BlockState field_236619_b_ = Blocks.AIR.getDefaultState();
      private Vector3i field_236620_c_ = Vector3i.NULL_VECTOR;
      private Vector3i field_236621_d_ = Vector3i.NULL_VECTOR;

      public NetherackBlobReplacementFeature.Builder func_236623_a_(BlockState p_236623_1_) {
         this.field_236618_a_ = p_236623_1_;
         return this;
      }

      public NetherackBlobReplacementFeature.Builder func_236625_b_(BlockState p_236625_1_) {
         this.field_236619_b_ = p_236625_1_;
         return this;
      }

      public NetherackBlobReplacementFeature.Builder func_236624_a_(Vector3i p_236624_1_) {
         this.field_236620_c_ = p_236624_1_;
         return this;
      }

      public NetherackBlobReplacementFeature.Builder func_236626_b_(Vector3i p_236626_1_) {
         this.field_236621_d_ = p_236626_1_;
         return this;
      }

      public NetherackBlobReplacementFeature func_236622_a_() {
         if (this.field_236620_c_.getX() >= 0 && this.field_236620_c_.getY() >= 0 && this.field_236620_c_.getZ() >= 0) {
            if (this.field_236620_c_.getX() <= this.field_236621_d_.getX() && this.field_236620_c_.getY() <= this.field_236621_d_.getY() && this.field_236620_c_.getZ() <= this.field_236621_d_.getZ()) {
               return new NetherackBlobReplacementFeature(this.field_236618_a_, this.field_236619_b_, this.field_236620_c_, this.field_236621_d_);
            } else {
               throw new IllegalArgumentException("Maximum reach must be greater than minimum reach for each axis");
            }
         } else {
            throw new IllegalArgumentException("Minimum reach cannot be less than zero");
         }
      }
   }
}