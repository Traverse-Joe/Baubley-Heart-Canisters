package net.minecraft.world.gen.feature.jigsaw;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.mojang.datafixers.Dynamic;
import com.mojang.datafixers.types.DynamicOps;
import java.util.List;
import java.util.Random;
import net.minecraft.block.Blocks;
import net.minecraft.block.JigsawBlock;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Direction;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.template.Template;
import net.minecraft.world.gen.feature.template.TemplateManager;

public class FeatureJigsawPiece extends JigsawPiece {
   private final ConfiguredFeature<?, ?> field_214870_a;
   private final CompoundNBT field_214871_b;

   @Deprecated
   public FeatureJigsawPiece(ConfiguredFeature<?, ?> p_i51409_1_) {
      this(p_i51409_1_, JigsawPattern.PlacementBehaviour.RIGID);
   }

   public FeatureJigsawPiece(ConfiguredFeature<?, ?> p_i51410_1_, JigsawPattern.PlacementBehaviour p_i51410_2_) {
      super(p_i51410_2_);
      this.field_214870_a = p_i51410_1_;
      this.field_214871_b = this.func_214869_b();
   }

   public <T> FeatureJigsawPiece(Dynamic<T> p_i51411_1_) {
      super(p_i51411_1_);
      this.field_214870_a = ConfiguredFeature.deserialize(p_i51411_1_.get("feature").orElseEmptyMap());
      this.field_214871_b = this.func_214869_b();
   }

   public CompoundNBT func_214869_b() {
      CompoundNBT compoundnbt = new CompoundNBT();
      compoundnbt.putString("target_pool", "minecraft:empty");
      compoundnbt.putString("attachement_type", "minecraft:bottom");
      compoundnbt.putString("final_state", "minecraft:air");
      return compoundnbt;
   }

   public BlockPos func_214868_a(TemplateManager p_214868_1_, Rotation p_214868_2_) {
      return BlockPos.ZERO;
   }

   public List<Template.BlockInfo> func_214849_a(TemplateManager p_214849_1_, BlockPos p_214849_2_, Rotation p_214849_3_, Random p_214849_4_) {
      List<Template.BlockInfo> list = Lists.newArrayList();
      list.add(new Template.BlockInfo(p_214849_2_, Blocks.field_226904_lY_.getDefaultState().with(JigsawBlock.FACING, Direction.DOWN), this.field_214871_b));
      return list;
   }

   public MutableBoundingBox func_214852_a(TemplateManager p_214852_1_, BlockPos p_214852_2_, Rotation p_214852_3_) {
      BlockPos blockpos = this.func_214868_a(p_214852_1_, p_214852_3_);
      return new MutableBoundingBox(p_214852_2_.getX(), p_214852_2_.getY(), p_214852_2_.getZ(), p_214852_2_.getX() + blockpos.getX(), p_214852_2_.getY() + blockpos.getY(), p_214852_2_.getZ() + blockpos.getZ());
   }

   public boolean func_225575_a_(TemplateManager p_225575_1_, IWorld p_225575_2_, ChunkGenerator<?> p_225575_3_, BlockPos p_225575_4_, Rotation p_225575_5_, MutableBoundingBox p_225575_6_, Random p_225575_7_) {
      return this.field_214870_a.place(p_225575_2_, p_225575_3_, p_225575_7_, p_225575_4_);
   }

   public <T> Dynamic<T> serialize0(DynamicOps<T> p_214851_1_) {
      return new Dynamic<>(p_214851_1_, p_214851_1_.createMap(ImmutableMap.of(p_214851_1_.createString("feature"), this.field_214870_a.serialize(p_214851_1_).getValue())));
   }

   public IJigsawDeserializer getType() {
      return IJigsawDeserializer.FEATURE_POOL_ELEMENT;
   }

   public String toString() {
      return "Feature[" + Registry.FEATURE.getKey(this.field_214870_a.feature) + "]";
   }
}