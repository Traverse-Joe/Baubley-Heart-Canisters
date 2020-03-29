package net.minecraft.world.gen.feature;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.mojang.datafixers.Dynamic;
import com.mojang.datafixers.types.DynamicOps;
import java.util.List;
import net.minecraft.world.gen.blockstateprovider.BlockStateProvider;
import net.minecraft.world.gen.treedecorator.TreeDecorator;

public class HugeTreeFeatureConfig extends BaseTreeFeatureConfig {
   public final int field_227275_a_;
   public final int field_227276_b_;

   protected HugeTreeFeatureConfig(BlockStateProvider p_i225833_1_, BlockStateProvider p_i225833_2_, List<TreeDecorator> p_i225833_3_, int p_i225833_4_, int p_i225833_5_, int p_i225833_6_) {
      super(p_i225833_1_, p_i225833_2_, p_i225833_3_, p_i225833_4_);
      this.field_227275_a_ = p_i225833_5_;
      this.field_227276_b_ = p_i225833_6_;
   }

   public <T> Dynamic<T> serialize(DynamicOps<T> ops) {
      Dynamic<T> dynamic = new Dynamic<>(ops, ops.createMap(ImmutableMap.of(ops.createString("height_interval"), ops.createInt(this.field_227275_a_), ops.createString("crown_height"), ops.createInt(this.field_227276_b_))));
      return dynamic.merge(super.serialize(ops));
   }

   @Override
   protected HugeTreeFeatureConfig setSapling(net.minecraftforge.common.IPlantable value) {
      super.setSapling(value);
      return this;
   }

   public static <T> HugeTreeFeatureConfig func_227277_a_(Dynamic<T> p_227277_0_) {
      BaseTreeFeatureConfig basetreefeatureconfig = BaseTreeFeatureConfig.func_227376_b_(p_227277_0_);
      return new HugeTreeFeatureConfig(basetreefeatureconfig.field_227368_m_, basetreefeatureconfig.field_227369_n_, basetreefeatureconfig.field_227370_o_, basetreefeatureconfig.field_227371_p_, p_227277_0_.get("height_interval").asInt(0), p_227277_0_.get("crown_height").asInt(0));
   }

   public static <T> HugeTreeFeatureConfig deserializeDarkOak(Dynamic<T> data) {
      return func_227277_a_(data).setSapling((net.minecraftforge.common.IPlantable)net.minecraft.block.Blocks.DARK_OAK_SAPLING);
   }
   public static <T> HugeTreeFeatureConfig deserializeSpruce(Dynamic<T> data) {
      return func_227277_a_(data).setSapling((net.minecraftforge.common.IPlantable)net.minecraft.block.Blocks.SPRUCE_SAPLING);
   }
   public static <T> HugeTreeFeatureConfig deserializeJungle(Dynamic<T> data) {
      return func_227277_a_(data).setSapling((net.minecraftforge.common.IPlantable)net.minecraft.block.Blocks.JUNGLE_SAPLING);
   }

   public static class Builder extends BaseTreeFeatureConfig.Builder {
      private List<TreeDecorator> field_227278_c_ = ImmutableList.of();
      private int field_227279_d_;
      private int field_227280_e_;
      private int field_227281_f_;

      public Builder(BlockStateProvider p_i225834_1_, BlockStateProvider p_i225834_2_) {
         super(p_i225834_1_, p_i225834_2_);
      }

      public HugeTreeFeatureConfig.Builder func_227282_a_(List<TreeDecorator> p_227282_1_) {
         this.field_227278_c_ = p_227282_1_;
         return this;
      }

      public HugeTreeFeatureConfig.Builder func_225569_d_(int p_225569_1_) {
         this.field_227279_d_ = p_225569_1_;
         return this;
      }

      public HugeTreeFeatureConfig.Builder func_227283_b_(int p_227283_1_) {
         this.field_227280_e_ = p_227283_1_;
         return this;
      }

      public HugeTreeFeatureConfig.Builder func_227284_c_(int p_227284_1_) {
         this.field_227281_f_ = p_227284_1_;
         return this;
      }

      @Override
      public HugeTreeFeatureConfig.Builder setSapling(net.minecraftforge.common.IPlantable value) {
         super.setSapling(value);
         return this;
      }

      public HugeTreeFeatureConfig func_225568_b_() {
         return new HugeTreeFeatureConfig(this.field_227377_a_, this.field_227378_b_, this.field_227278_c_, this.field_227279_d_, this.field_227280_e_, this.field_227281_f_).setSapling(this.sapling);
      }
   }
}