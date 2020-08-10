package net.minecraft.world.gen.feature.jigsaw;

import com.google.common.collect.Lists;
import com.google.common.collect.Queues;
import java.util.Deque;
import java.util.List;
import java.util.Random;
import net.minecraft.block.JigsawBlock;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.util.math.shapes.IBooleanFunction;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.feature.structure.AbstractVillagePiece;
import net.minecraft.world.gen.feature.structure.BastionRemnantsPieces;
import net.minecraft.world.gen.feature.structure.PillagerOutpostPieces;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.gen.feature.structure.VillagePieces;
import net.minecraft.world.gen.feature.template.Template;
import net.minecraft.world.gen.feature.template.TemplateManager;
import org.apache.commons.lang3.mutable.MutableObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class JigsawManager {
   private static final Logger LOGGER = LogManager.getLogger();
   public static final JigsawPatternRegistry REGISTRY = new JigsawPatternRegistry();

   public static void func_236821_a_() {
      BastionRemnantsPieces.func_236258_a_();
      VillagePieces.func_236425_a_();
      PillagerOutpostPieces.func_236999_a_();
   }

   public static void func_236823_a_(ResourceLocation p_236823_0_, int p_236823_1_, JigsawManager.IPieceFactory p_236823_2_, ChunkGenerator p_236823_3_, TemplateManager p_236823_4_, BlockPos p_236823_5_, List<? super AbstractVillagePiece> p_236823_6_, Random p_236823_7_, boolean p_236823_8_, boolean p_236823_9_) {
      Structure.func_236397_g_();
      Rotation rotation = Rotation.randomRotation(p_236823_7_);
      JigsawPattern jigsawpattern = REGISTRY.get(p_236823_0_);
      JigsawPiece jigsawpiece = jigsawpattern.getRandomPiece(p_236823_7_);
      AbstractVillagePiece abstractvillagepiece = p_236823_2_.create(p_236823_4_, jigsawpiece, p_236823_5_, jigsawpiece.getGroundLevelDelta(), rotation, jigsawpiece.getBoundingBox(p_236823_4_, p_236823_5_, rotation));
      MutableBoundingBox mutableboundingbox = abstractvillagepiece.getBoundingBox();
      int i = (mutableboundingbox.maxX + mutableboundingbox.minX) / 2;
      int j = (mutableboundingbox.maxZ + mutableboundingbox.minZ) / 2;
      int k;
      if (p_236823_9_) {
         k = p_236823_5_.getY() + p_236823_3_.func_222532_b(i, j, Heightmap.Type.WORLD_SURFACE_WG);
      } else {
         k = p_236823_5_.getY();
      }

      int l = mutableboundingbox.minY + abstractvillagepiece.getGroundLevelDelta();
      abstractvillagepiece.offset(0, k - l, 0);
      p_236823_6_.add(abstractvillagepiece);
      if (p_236823_1_ > 0) {
         int i1 = 80;
         AxisAlignedBB axisalignedbb = new AxisAlignedBB((double)(i - 80), (double)(k - 80), (double)(j - 80), (double)(i + 80 + 1), (double)(k + 80 + 1), (double)(j + 80 + 1));
         JigsawManager.Assembler jigsawmanager$assembler = new JigsawManager.Assembler(p_236823_1_, p_236823_2_, p_236823_3_, p_236823_4_, p_236823_6_, p_236823_7_);
         jigsawmanager$assembler.availablePieces.addLast(new JigsawManager.Entry(abstractvillagepiece, new MutableObject<>(VoxelShapes.combineAndSimplify(VoxelShapes.create(axisalignedbb), VoxelShapes.create(AxisAlignedBB.toImmutable(mutableboundingbox)), IBooleanFunction.ONLY_FIRST)), k + 80, 0));

         while(!jigsawmanager$assembler.availablePieces.isEmpty()) {
            JigsawManager.Entry jigsawmanager$entry = jigsawmanager$assembler.availablePieces.removeFirst();
            jigsawmanager$assembler.func_236831_a_(jigsawmanager$entry.villagePiece, jigsawmanager$entry.free, jigsawmanager$entry.boundsTop, jigsawmanager$entry.depth, p_236823_8_);
         }

      }
   }

   public static void func_236822_a_(AbstractVillagePiece p_236822_0_, int p_236822_1_, JigsawManager.IPieceFactory p_236822_2_, ChunkGenerator p_236822_3_, TemplateManager p_236822_4_, List<? super AbstractVillagePiece> p_236822_5_, Random p_236822_6_) {
      func_236821_a_();
      JigsawManager.Assembler jigsawmanager$assembler = new JigsawManager.Assembler(p_236822_1_, p_236822_2_, p_236822_3_, p_236822_4_, p_236822_5_, p_236822_6_);
      jigsawmanager$assembler.availablePieces.addLast(new JigsawManager.Entry(p_236822_0_, new MutableObject<>(VoxelShapes.INFINITY), 0, 0));

      while(!jigsawmanager$assembler.availablePieces.isEmpty()) {
         JigsawManager.Entry jigsawmanager$entry = jigsawmanager$assembler.availablePieces.removeFirst();
         jigsawmanager$assembler.func_236831_a_(jigsawmanager$entry.villagePiece, jigsawmanager$entry.free, jigsawmanager$entry.boundsTop, jigsawmanager$entry.depth, false);
      }

   }

   static {
      REGISTRY.register(JigsawPattern.EMPTY);
   }

   static final class Assembler {
      private final int maxDepth;
      private final JigsawManager.IPieceFactory pieceFactory;
      private final ChunkGenerator chunkGenerator;
      private final TemplateManager templateManager;
      private final List<? super AbstractVillagePiece> structurePieces;
      private final Random rand;
      private final Deque<JigsawManager.Entry> availablePieces = Queues.newArrayDeque();

      private Assembler(int p_i232044_1_, JigsawManager.IPieceFactory p_i232044_2_, ChunkGenerator p_i232044_3_, TemplateManager p_i232044_4_, List<? super AbstractVillagePiece> p_i232044_5_, Random p_i232044_6_) {
         this.maxDepth = p_i232044_1_;
         this.pieceFactory = p_i232044_2_;
         this.chunkGenerator = p_i232044_3_;
         this.templateManager = p_i232044_4_;
         this.structurePieces = p_i232044_5_;
         this.rand = p_i232044_6_;
      }

      private void func_236831_a_(AbstractVillagePiece p_236831_1_, MutableObject<VoxelShape> p_236831_2_, int p_236831_3_, int p_236831_4_, boolean p_236831_5_) {
         JigsawPiece jigsawpiece = p_236831_1_.getJigsawPiece();
         BlockPos blockpos = p_236831_1_.getPos();
         Rotation rotation = p_236831_1_.getRotation();
         JigsawPattern.PlacementBehaviour jigsawpattern$placementbehaviour = jigsawpiece.getPlacementBehaviour();
         boolean flag = jigsawpattern$placementbehaviour == JigsawPattern.PlacementBehaviour.RIGID;
         MutableObject<VoxelShape> mutableobject = new MutableObject<>();
         MutableBoundingBox mutableboundingbox = p_236831_1_.getBoundingBox();
         int i = mutableboundingbox.minY;

         label127:
         for(Template.BlockInfo template$blockinfo : jigsawpiece.getJigsawBlocks(this.templateManager, blockpos, rotation, this.rand)) {
            Direction direction = JigsawBlock.func_235508_h_(template$blockinfo.state);
            BlockPos blockpos1 = template$blockinfo.pos;
            BlockPos blockpos2 = blockpos1.offset(direction);
            int j = blockpos1.getY() - i;
            int k = -1;
            JigsawPattern jigsawpattern = JigsawManager.REGISTRY.get(new ResourceLocation(template$blockinfo.nbt.getString("pool")));
            JigsawPattern jigsawpattern1 = JigsawManager.REGISTRY.get(jigsawpattern.getFallback());
            if (jigsawpattern != JigsawPattern.INVALID && (jigsawpattern.getNumberOfPieces() != 0 || jigsawpattern == JigsawPattern.EMPTY)) {
               boolean flag1 = mutableboundingbox.isVecInside(blockpos2);
               MutableObject<VoxelShape> mutableobject1;
               int l;
               if (flag1) {
                  mutableobject1 = mutableobject;
                  l = i;
                  if (mutableobject.getValue() == null) {
                     mutableobject.setValue(VoxelShapes.create(AxisAlignedBB.toImmutable(mutableboundingbox)));
                  }
               } else {
                  mutableobject1 = p_236831_2_;
                  l = p_236831_3_;
               }

               List<JigsawPiece> list = Lists.newArrayList();
               if (p_236831_4_ != this.maxDepth) {
                  list.addAll(jigsawpattern.getShuffledPieces(this.rand));
               }

               list.addAll(jigsawpattern1.getShuffledPieces(this.rand));

               for(JigsawPiece jigsawpiece1 : list) {
                  if (jigsawpiece1 == EmptyJigsawPiece.INSTANCE) {
                     break;
                  }

                  for(Rotation rotation1 : Rotation.shuffledRotations(this.rand)) {
                     List<Template.BlockInfo> list1 = jigsawpiece1.getJigsawBlocks(this.templateManager, BlockPos.ZERO, rotation1, this.rand);
                     MutableBoundingBox mutableboundingbox1 = jigsawpiece1.getBoundingBox(this.templateManager, BlockPos.ZERO, rotation1);
                     int i1;
                     if (p_236831_5_ && mutableboundingbox1.getYSize() <= 16) {
                        i1 = list1.stream().mapToInt((p_214880_2_) -> {
                           if (!mutableboundingbox1.isVecInside(p_214880_2_.pos.offset(JigsawBlock.func_235508_h_(p_214880_2_.state)))) {
                              return 0;
                           } else {
                              ResourceLocation resourcelocation = new ResourceLocation(p_214880_2_.nbt.getString("pool"));
                              JigsawPattern jigsawpattern2 = JigsawManager.REGISTRY.get(resourcelocation);
                              JigsawPattern jigsawpattern3 = JigsawManager.REGISTRY.get(jigsawpattern2.getFallback());
                              return Math.max(jigsawpattern2.getMaxSize(this.templateManager), jigsawpattern3.getMaxSize(this.templateManager));
                           }
                        }).max().orElse(0);
                     } else {
                        i1 = 0;
                     }

                     for(Template.BlockInfo template$blockinfo1 : list1) {
                        if (JigsawBlock.func_220171_a(template$blockinfo, template$blockinfo1)) {
                           BlockPos blockpos3 = template$blockinfo1.pos;
                           BlockPos blockpos4 = new BlockPos(blockpos2.getX() - blockpos3.getX(), blockpos2.getY() - blockpos3.getY(), blockpos2.getZ() - blockpos3.getZ());
                           MutableBoundingBox mutableboundingbox2 = jigsawpiece1.getBoundingBox(this.templateManager, blockpos4, rotation1);
                           int j1 = mutableboundingbox2.minY;
                           JigsawPattern.PlacementBehaviour jigsawpattern$placementbehaviour1 = jigsawpiece1.getPlacementBehaviour();
                           boolean flag2 = jigsawpattern$placementbehaviour1 == JigsawPattern.PlacementBehaviour.RIGID;
                           int k1 = blockpos3.getY();
                           int l1 = j - k1 + JigsawBlock.func_235508_h_(template$blockinfo.state).getYOffset();
                           int i2;
                           if (flag && flag2) {
                              i2 = i + l1;
                           } else {
                              if (k == -1) {
                                 k = this.chunkGenerator.func_222532_b(blockpos1.getX(), blockpos1.getZ(), Heightmap.Type.WORLD_SURFACE_WG);
                              }

                              i2 = k - k1;
                           }

                           int j2 = i2 - j1;
                           MutableBoundingBox mutableboundingbox3 = mutableboundingbox2.func_215127_b(0, j2, 0);
                           BlockPos blockpos5 = blockpos4.add(0, j2, 0);
                           if (i1 > 0) {
                              int k2 = Math.max(i1 + 1, mutableboundingbox3.maxY - mutableboundingbox3.minY);
                              mutableboundingbox3.maxY = mutableboundingbox3.minY + k2;
                           }

                           if (!VoxelShapes.compare(mutableobject1.getValue(), VoxelShapes.create(AxisAlignedBB.toImmutable(mutableboundingbox3).shrink(0.25D)), IBooleanFunction.ONLY_SECOND)) {
                              mutableobject1.setValue(VoxelShapes.combine(mutableobject1.getValue(), VoxelShapes.create(AxisAlignedBB.toImmutable(mutableboundingbox3)), IBooleanFunction.ONLY_FIRST));
                              int j3 = p_236831_1_.getGroundLevelDelta();
                              int l2;
                              if (flag2) {
                                 l2 = j3 - l1;
                              } else {
                                 l2 = jigsawpiece1.getGroundLevelDelta();
                              }

                              AbstractVillagePiece abstractvillagepiece = this.pieceFactory.create(this.templateManager, jigsawpiece1, blockpos5, l2, rotation1, mutableboundingbox3);
                              int i3;
                              if (flag) {
                                 i3 = i + j;
                              } else if (flag2) {
                                 i3 = i2 + k1;
                              } else {
                                 if (k == -1) {
                                    k = this.chunkGenerator.func_222532_b(blockpos1.getX(), blockpos1.getZ(), Heightmap.Type.WORLD_SURFACE_WG);
                                 }

                                 i3 = k + l1 / 2;
                              }

                              p_236831_1_.addJunction(new JigsawJunction(blockpos2.getX(), i3 - j + j3, blockpos2.getZ(), l1, jigsawpattern$placementbehaviour1));
                              abstractvillagepiece.addJunction(new JigsawJunction(blockpos1.getX(), i3 - k1 + l2, blockpos1.getZ(), -l1, jigsawpattern$placementbehaviour));
                              this.structurePieces.add(abstractvillagepiece);
                              if (p_236831_4_ + 1 <= this.maxDepth) {
                                 this.availablePieces.addLast(new JigsawManager.Entry(abstractvillagepiece, mutableobject1, l, p_236831_4_ + 1));
                              }
                              continue label127;
                           }
                        }
                     }
                  }
               }
            } else {
               JigsawManager.LOGGER.warn("Empty or none existent pool: {}", (Object)template$blockinfo.nbt.getString("pool"));
            }
         }

      }
   }

   static final class Entry {
      private final AbstractVillagePiece villagePiece;
      private final MutableObject<VoxelShape> free;
      private final int boundsTop;
      private final int depth;

      private Entry(AbstractVillagePiece p_i232042_1_, MutableObject<VoxelShape> p_i232042_2_, int p_i232042_3_, int p_i232042_4_) {
         this.villagePiece = p_i232042_1_;
         this.free = p_i232042_2_;
         this.boundsTop = p_i232042_3_;
         this.depth = p_i232042_4_;
      }
   }

   public interface IPieceFactory {
      AbstractVillagePiece create(TemplateManager p_create_1_, JigsawPiece p_create_2_, BlockPos p_create_3_, int p_create_4_, Rotation p_create_5_, MutableBoundingBox p_create_6_);
   }
}