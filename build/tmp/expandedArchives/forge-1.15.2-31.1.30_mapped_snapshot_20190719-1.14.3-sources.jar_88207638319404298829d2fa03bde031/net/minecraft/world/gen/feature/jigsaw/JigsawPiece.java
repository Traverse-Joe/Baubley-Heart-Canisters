package net.minecraft.world.gen.feature.jigsaw;

import com.mojang.datafixers.Dynamic;
import com.mojang.datafixers.types.DynamicOps;
import java.util.List;
import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.template.Template;
import net.minecraft.world.gen.feature.template.TemplateManager;

public abstract class JigsawPiece {
   @Nullable
   private volatile JigsawPattern.PlacementBehaviour projection;

   protected JigsawPiece(JigsawPattern.PlacementBehaviour projection) {
      this.projection = projection;
   }

   protected JigsawPiece(Dynamic<?> p_i51399_1_) {
      this.projection = JigsawPattern.PlacementBehaviour.func_214938_a(p_i51399_1_.get("projection").asString(JigsawPattern.PlacementBehaviour.RIGID.func_214936_a()));
   }

   public abstract List<Template.BlockInfo> func_214849_a(TemplateManager p_214849_1_, BlockPos p_214849_2_, Rotation p_214849_3_, Random p_214849_4_);

   public abstract MutableBoundingBox func_214852_a(TemplateManager p_214852_1_, BlockPos p_214852_2_, Rotation p_214852_3_);

   public abstract boolean func_225575_a_(TemplateManager p_225575_1_, IWorld p_225575_2_, ChunkGenerator<?> p_225575_3_, BlockPos p_225575_4_, Rotation p_225575_5_, MutableBoundingBox p_225575_6_, Random p_225575_7_);

   public abstract IJigsawDeserializer getType();

   public void func_214846_a(IWorld p_214846_1_, Template.BlockInfo p_214846_2_, BlockPos p_214846_3_, Rotation p_214846_4_, Random p_214846_5_, MutableBoundingBox p_214846_6_) {
   }

   public JigsawPiece setPlacementBehaviour(JigsawPattern.PlacementBehaviour p_214845_1_) {
      this.projection = p_214845_1_;
      return this;
   }

   public JigsawPattern.PlacementBehaviour getPlacementBehaviour() {
      JigsawPattern.PlacementBehaviour jigsawpattern$placementbehaviour = this.projection;
      if (jigsawpattern$placementbehaviour == null) {
         throw new IllegalStateException();
      } else {
         return jigsawpattern$placementbehaviour;
      }
   }

   protected abstract <T> Dynamic<T> serialize0(DynamicOps<T> p_214851_1_);

   public <T> Dynamic<T> serialize(DynamicOps<T> p_214847_1_) {
      T t = this.serialize0(p_214847_1_).getValue();
      T t1 = p_214847_1_.mergeInto(t, p_214847_1_.createString("element_type"), p_214847_1_.createString(Registry.STRUCTURE_POOL_ELEMENT.getKey(this.getType()).toString()));
      return new Dynamic<>(p_214847_1_, p_214847_1_.mergeInto(t1, p_214847_1_.createString("projection"), p_214847_1_.createString(this.projection.func_214936_a())));
   }

   public int func_214850_d() {
      return 1;
   }
}