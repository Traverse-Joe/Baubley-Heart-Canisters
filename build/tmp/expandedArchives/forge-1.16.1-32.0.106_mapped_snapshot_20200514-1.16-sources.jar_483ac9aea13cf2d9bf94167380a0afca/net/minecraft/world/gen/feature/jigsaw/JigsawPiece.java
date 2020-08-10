package net.minecraft.world.gen.feature.jigsaw;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.List;
import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.structure.StructureManager;
import net.minecraft.world.gen.feature.template.Template;
import net.minecraft.world.gen.feature.template.TemplateManager;

public abstract class JigsawPiece {
   public static final Codec<JigsawPiece> field_236847_e_ = Registry.STRUCTURE_POOL_ELEMENT.dispatch("element_type", JigsawPiece::getType, IJigsawDeserializer::codec);
   @Nullable
   private volatile JigsawPattern.PlacementBehaviour projection;

   protected static <E extends JigsawPiece> RecordCodecBuilder<E, JigsawPattern.PlacementBehaviour> func_236848_d_() {
      return JigsawPattern.PlacementBehaviour.field_236858_c_.fieldOf("projection").forGetter(JigsawPiece::getPlacementBehaviour);
   }

   protected JigsawPiece(JigsawPattern.PlacementBehaviour projection) {
      this.projection = projection;
   }

   public abstract List<Template.BlockInfo> getJigsawBlocks(TemplateManager templateManagerIn, BlockPos pos, Rotation rotationIn, Random rand);

   public abstract MutableBoundingBox getBoundingBox(TemplateManager templateManagerIn, BlockPos pos, Rotation rotationIn);

   public abstract boolean func_230378_a_(TemplateManager p_230378_1_, ISeedReader p_230378_2_, StructureManager p_230378_3_, ChunkGenerator p_230378_4_, BlockPos p_230378_5_, BlockPos p_230378_6_, Rotation p_230378_7_, MutableBoundingBox p_230378_8_, Random p_230378_9_, boolean p_230378_10_);

   public abstract IJigsawDeserializer<?> getType();

   public void handleDataMarker(IWorld worldIn, Template.BlockInfo p_214846_2_, BlockPos pos, Rotation rotationIn, Random rand, MutableBoundingBox p_214846_6_) {
   }

   public JigsawPiece setPlacementBehaviour(JigsawPattern.PlacementBehaviour placementBehaviour) {
      this.projection = placementBehaviour;
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

   public int getGroundLevelDelta() {
      return 1;
   }
}