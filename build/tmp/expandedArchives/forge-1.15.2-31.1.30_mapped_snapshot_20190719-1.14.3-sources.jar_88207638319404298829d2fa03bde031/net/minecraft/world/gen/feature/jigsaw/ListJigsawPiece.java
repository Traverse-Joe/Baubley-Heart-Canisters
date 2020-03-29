package net.minecraft.world.gen.feature.jigsaw;

import com.google.common.collect.ImmutableMap;
import com.mojang.datafixers.Dynamic;
import com.mojang.datafixers.types.DynamicOps;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import net.minecraft.util.IDynamicDeserializer;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.template.Template;
import net.minecraft.world.gen.feature.template.TemplateManager;

public class ListJigsawPiece extends JigsawPiece {
   private final List<JigsawPiece> elements;

   @Deprecated
   public ListJigsawPiece(List<JigsawPiece> p_i51404_1_) {
      this(p_i51404_1_, JigsawPattern.PlacementBehaviour.RIGID);
   }

   public ListJigsawPiece(List<JigsawPiece> p_i51405_1_, JigsawPattern.PlacementBehaviour p_i51405_2_) {
      super(p_i51405_2_);
      if (p_i51405_1_.isEmpty()) {
         throw new IllegalArgumentException("Elements are empty");
      } else {
         this.elements = p_i51405_1_;
         this.func_214864_b(p_i51405_2_);
      }
   }

   public ListJigsawPiece(Dynamic<?> p_i51406_1_) {
      super(p_i51406_1_);
      List<JigsawPiece> list = p_i51406_1_.get("elements").asList((p_214866_0_) -> {
         return IDynamicDeserializer.func_214907_a(p_214866_0_, Registry.STRUCTURE_POOL_ELEMENT, "element_type", EmptyJigsawPiece.INSTANCE);
      });
      if (list.isEmpty()) {
         throw new IllegalArgumentException("Elements are empty");
      } else {
         this.elements = list;
      }
   }

   public List<Template.BlockInfo> func_214849_a(TemplateManager p_214849_1_, BlockPos p_214849_2_, Rotation p_214849_3_, Random p_214849_4_) {
      return this.elements.get(0).func_214849_a(p_214849_1_, p_214849_2_, p_214849_3_, p_214849_4_);
   }

   public MutableBoundingBox func_214852_a(TemplateManager p_214852_1_, BlockPos p_214852_2_, Rotation p_214852_3_) {
      MutableBoundingBox mutableboundingbox = MutableBoundingBox.getNewBoundingBox();

      for(JigsawPiece jigsawpiece : this.elements) {
         MutableBoundingBox mutableboundingbox1 = jigsawpiece.func_214852_a(p_214852_1_, p_214852_2_, p_214852_3_);
         mutableboundingbox.expandTo(mutableboundingbox1);
      }

      return mutableboundingbox;
   }

   public boolean func_225575_a_(TemplateManager p_225575_1_, IWorld p_225575_2_, ChunkGenerator<?> p_225575_3_, BlockPos p_225575_4_, Rotation p_225575_5_, MutableBoundingBox p_225575_6_, Random p_225575_7_) {
      for(JigsawPiece jigsawpiece : this.elements) {
         if (!jigsawpiece.func_225575_a_(p_225575_1_, p_225575_2_, p_225575_3_, p_225575_4_, p_225575_5_, p_225575_6_, p_225575_7_)) {
            return false;
         }
      }

      return true;
   }

   public IJigsawDeserializer getType() {
      return IJigsawDeserializer.LIST_POOL_ELEMENT;
   }

   public JigsawPiece setPlacementBehaviour(JigsawPattern.PlacementBehaviour p_214845_1_) {
      super.setPlacementBehaviour(p_214845_1_);
      this.func_214864_b(p_214845_1_);
      return this;
   }

   public <T> Dynamic<T> serialize0(DynamicOps<T> p_214851_1_) {
      T t = p_214851_1_.createList(this.elements.stream().map((p_214865_1_) -> {
         return p_214865_1_.serialize(p_214851_1_).getValue();
      }));
      return new Dynamic<>(p_214851_1_, p_214851_1_.createMap(ImmutableMap.of(p_214851_1_.createString("elements"), t)));
   }

   public String toString() {
      return "List[" + (String)this.elements.stream().map(Object::toString).collect(Collectors.joining(", ")) + "]";
   }

   private void func_214864_b(JigsawPattern.PlacementBehaviour p_214864_1_) {
      this.elements.forEach((p_214863_1_) -> {
         p_214863_1_.setPlacementBehaviour(p_214864_1_);
      });
   }
}